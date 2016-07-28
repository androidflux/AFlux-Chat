package com.example.flux.android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.flux.android.actions.ChatActionCreator;
import com.example.flux.android.model.Thread;
import com.example.flux.android.stores.ThreadStore;
import com.example.flux.android.stores.Store;
import com.example.flux.android.views.custom.BadgeLayout;
import com.squareup.otto.Subscribe;

import java.util.Collections;
import java.util.List;

public class ThreadActivity extends AppCompatActivity {
    private ThreadStore store = Flux.getStore(ThreadStore.class);
    private ChatActionCreator actionCreator = null;

    private MessageAdapter messageAdapter = null;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        initView();
        actionCreator = ChatActionCreator.getCreator(Flux.getDispatcher());
        actionCreator.loadAllThread();
    }

    private void initView() {
        // init toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // the others
        listView = (ListView)findViewById(R.id.message_listView);
        messageAdapter = new MessageAdapter(this);
        listView.setAdapter(messageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Thread thread = (Thread) adapterView.getAdapter().getItem(i);
                // TODO Action !!
                onClickThreadItem(thread);
            }
        });
    }

    private void render() {
        messageAdapter.setMessages(store.getThreads());
        messageAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onStoreChange(Store.StoreChangeEvent event) {
        render();
    }

    @Subscribe
    public void onNavEvent(String event) {
        startActivity(new Intent(this, MessageActivity.class));
    }

    public void onClickThreadItem(Thread thread) {
        actionCreator.clickThread(thread);
    }

    @Override
    protected void onPause() {
        super.onPause();
        store.unregister(this);
    }

    /**
     * 在Activity切入后台之后，Store可能会发生变化，
     * 这个时候要主动渲染更新当前的UI
     */
    @Override
    protected void onResume() {
        super.onResume();
        store.register(this);
        render();
    }

    static class MessageAdapter extends BaseAdapter {
        private List<Thread> messages = Collections.EMPTY_LIST;
        private Context context;

        MessageAdapter(Context context) {
            this.context = context;
        }

        public void setMessages(List<Thread> messages) {
            this.messages = messages;
        }

        public List<Thread> getMessages() {
            return messages;
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int i) {
            return messages.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.layout_item_message, null);
                ViewHolder holder = ViewHolder.newViewHolder(view);
                view.setTag(holder);
            }

            render((ViewHolder)view.getTag(), (Thread)messages.get(i));

            return view;
        }

        private void render(ViewHolder holder, Thread message) {
            holder.badgeView.setBadgeVisibility(!message.isRead());
            holder.imageAvatar.setImageResource(message.getAvatar());
            holder.textTitle.setText(message.getTitle());
            holder.textBody.setText(message.getBody());
        }
    }

    static class ViewHolder {
        public BadgeLayout badgeView;
        public ImageView imageAvatar;
        public TextView textTitle;
        public TextView textBody;

        private ViewHolder(){}

        public static ViewHolder newViewHolder(View root) {
            ViewHolder holder = new ViewHolder();
            holder.badgeView = (BadgeLayout)root.findViewById(R.id.message_item_badge);
            holder.imageAvatar = (ImageView)root.findViewById(R.id.message_item_avatar);
            holder.textTitle = (TextView)root.findViewById(R.id.message_item_title);
            holder.textBody = (TextView)root.findViewById(R.id.message_item_body);
            return holder;
        }
    }
}
