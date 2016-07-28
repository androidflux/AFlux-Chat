package com.example.flux.android;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.flux.android.actions.ChatActionCreator;
import com.example.flux.android.model.Message;
import com.example.flux.android.stores.Store;
import com.example.flux.android.stores.MessageStore;
import com.squareup.otto.Subscribe;

import java.util.Collections;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private MessageStore store = Flux.getStore(MessageStore.class);
    private ChatActionCreator actionCreator;

    private ThreadAdapter threadAdapter = null;
    private ListView listView;
    private EditText inputText;

    private Button backButton;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();

        actionCreator = ChatActionCreator.getCreator(Flux.getDispatcher());
        actionCreator.loadMessage(store.getThreadId());
    }

    private void initView() {
        // setup toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        backButton = (Button)myToolbar.findViewById(R.id.tool_bar_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBack(view);
            }
        });

        titleText = (TextView)myToolbar.findViewById(R.id.tool_bar_title);

        // the others
        listView = (ListView)findViewById(R.id.thread_listView);
        threadAdapter = new ThreadAdapter(this);
        listView.setAdapter(threadAdapter);
        inputText = (EditText)findViewById(R.id.thread_input_text);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickSend(View view) {
        if (inputText.getText() == null)
            return;

        String text = inputText.getText().toString();
        inputText.setText(null);

        actionCreator.sendMessage(text, store.getThreadId());
    }

    private void render() {
        backButton.setText(store.getBackText());
        titleText.setText(store.getTitle());

        threadAdapter.setThreads(store.getThreads());
        threadAdapter.notifyDataSetChanged();

        scrollToBottom();
    }

    // 初次直接定位到最后，以后滚动
    private void scrollToBottom() {
        if (listView.getFirstVisiblePosition() == 0) {
            listView.setSelection(listView.getBottom());
        } else {
            listView.smoothScrollToPosition(listView.getBottom());
        }
    }

    @Subscribe
    public void onStoreChange(Store.StoreChangeEvent event) {
        render();
    }

    @Override
    protected void onPause() {
        super.onPause();
        store.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        store.register(this);
        render();
    }

    static class ThreadAdapter extends BaseAdapter {
        static int TYPE_RECEIVE = 0;
        static int TYPE_SEND = 1;

        private List<Message> threads = Collections.EMPTY_LIST;


        private Context context;

        ThreadAdapter(Context context) {
            this.context = context;
        }

        public void setThreads(List<Message> threads) {
            this.threads = threads;
        }

        @Override
        public int getCount() {
            return threads.size();
        }

        @Override
        public Object getItem(int i) {
            return threads.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return threads.get(position).getType();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = newView(getItemViewType(i));
            }

            render((ViewHolder)view.getTag(), (Message)getItem(i));

            return view;
        }

        private void render(ViewHolder holder, Message thread) {
            holder.imageAvatar.setImageResource(thread.getAvatar());
            holder.textText.setText(thread.getMessage());
        }

        private View newView(int type) {
            View view;
            if (type == 0) {
                view = LayoutInflater.from(context).inflate(R.layout.layout_item_thread_recev, null);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.layout_item_thread_send, null);
            }
            view.setTag(ViewHolder.newViewHolder(view));
            return view;
        }
    }

    static class ViewHolder {
        public ImageView imageAvatar;
        public TextView textText;

        public static ViewHolder newViewHolder(View view) {
            ViewHolder holder = new ViewHolder();
            holder.imageAvatar = (ImageView)view.findViewById(R.id.thread_item_avatar);
            holder.textText = (TextView)view.findViewById(R.id.thread_item_text);
            return holder;
        }
    }

}
