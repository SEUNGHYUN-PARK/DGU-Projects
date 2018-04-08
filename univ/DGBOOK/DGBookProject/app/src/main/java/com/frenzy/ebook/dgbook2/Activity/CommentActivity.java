package com.frenzy.ebook.dgbook2.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.DAO;
import com.frenzy.ebook.dgbook2.GetnSet.Comment;
import com.frenzy.ebook.dgbook2.GetnSet.CommentListItem;
import com.frenzy.ebook.dgbook2.R;
import com.frenzy.ebook.dgbook2.adapter.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 민경 on 2016-11-15.
 * Modified by YoungHoonKim on 2016-11-18.
 * Modified by SeungHyeonPark on 2016-11-18.
 */

/**
 * 클래스 이름 : CommentActivity extends AppCompatActivity implements CommentAdapter.ItemClickCallback
 * 주된 기능 : 구매자가 댓글을 통해 서적을 거래할 수 있게 도와줌
 *
 * 멤버변수
 * private RecyclerView recView : 리싸이클러뷰를 통해 전반적인 출력을 구현
 * private CommentAdapter adapter : 리싸이클러뷰에서 작동한 댓글이 하나의 객체화되는데, 이를 잡아줄 어뎁터
 * ArrayList listData : 데이터들을 담는 백터
 * Integer bookID : 책의 ID
 * String owner : 판매자
 * DAO dao : 데이터베이스와 연결시켜주는 객체
 * EditText content : 댓글 내용을 달수있게 하는 텍스트상자 객체
 * SharedPreferences pref : 글 작성자의 이름을 불러오기위한 객체
 * SharedPreferences.Editor preEditior : 위의 에디터역할
 *
 * 메소드
 * protected void onCreate(Bundle savedInstanceState)
 * 매개변수 : Bundle savedInstanceState
 * 기능 : 댓글을 쓸 수 있게하며, 그리고 작성한 댓글을 출력 할 수 있게 출력 해 줌.
 *
 * 내장 클래스
 * 클래스 이름 :  class selectComments extends AsyncTask<Integer, Void, ArrayList<Comment>>
 * 주 기능 : 댓글을 불러오는 작업
 * 멤버변수
 * ProgressDialog dialog : 로딩화면을 띄어줌
 * 메소드
 * protected void onPreExecute()
 * protected void onPostExecute(ArrayList<Comment> comments)
 * protected ArrayList<Comment> doInBackground(Integer... params)
 */

public class CommentActivity extends AppCompatActivity implements CommentAdapter.ItemClickCallback{

    private RecyclerView recView;
    private CommentAdapter adapter;
    private ArrayList listData;
    private Integer bookID;
    private String owner;
    private DAO dao;
    private EditText content;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        content=(EditText) findViewById(R.id.edit_comment);
        dao=new DAO();
        recView=(RecyclerView)findViewById(R.id.comment_rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        Button button_comment= (Button)findViewById(R.id.button_comment);
        pref = getSharedPreferences("DGBook", MODE_PRIVATE);
        prefEditor = pref.edit();
        Intent intent=getIntent();
        bookID=intent.getIntExtra("BookID",0);
        owner=intent.getStringExtra("Owner");

        button_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Comment comment=new Comment(pref.getString("id","fail"),owner,content.getText().toString(),
                        bookID,pref.getString("name","fail"));
                insertComment insertComment=new insertComment();
                insertComment.execute(comment);
            }
        });
        selectComments selectComments=new selectComments();
        selectComments.execute(bookID);


    }
    class selectComments extends AsyncTask<Integer, Void, ArrayList<Comment>> {
        ProgressDialog loading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(CommentActivity.this);
            loading.setMessage("검색중입니다!!");
            loading.setProgressStyle(loading.STYLE_SPINNER);
            loading.show();
        }

        //검색된 책에대한 정보를 처리
        @Override
        protected void onPostExecute(ArrayList<Comment> comments) {
            super.onPostExecute(comments);
            loading.dismiss();
            //책정보가 성공적으로 검색되었다면
            if(comments!=null) {
                listData=(ArrayList) getListData(comments);
                setRecyclerView();
            }
            else
                Toast.makeText(getApplicationContext(), "첫 댓글을 달아주세요!", Toast.LENGTH_LONG).show();
        }


        @Override
        protected ArrayList<Comment> doInBackground(Integer... params) {
            //dao 의 select 메소드로 책정보를 담은 task를 리턴
            ArrayList<Comment> task=null;
            task=dao.selectcomment(params[0]);
            return task;
        }
    }
    class insertComment extends AsyncTask<Comment, Void, ArrayList<Comment>> {
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(CommentActivity.this);
            loading.setMessage("입력중입니다!!");
            loading.setProgressStyle(loading.STYLE_SPINNER);
            loading.show();
        }

        //검색된 책에대한 정보를 처리
        @Override
        protected void onPostExecute(ArrayList<Comment> result) {
            super.onPostExecute(result);
            loading.dismiss();
            //책정보가 성공적으로 검색되었다면
            if (result != null) {
                listData = (ArrayList) getListData(result);
                setRecyclerView();
                Toast.makeText(getApplicationContext(), "댓글 입력 성공!", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getApplicationContext(), "댓글 입력 실패!", Toast.LENGTH_LONG).show();
        }
        @Override
        protected ArrayList<Comment> doInBackground(Comment... params) {
            //dao 의 select 메소드로 책정보를 담은 task를 리턴
            boolean task;
            task=dao.insertcomment(params[0]);
            ArrayList<Comment> task2=null;
            if(task==true) {
                task2 = dao.selectcomment(bookID);
                return task2;
            }
            else {
                task2 = null;
                return task2;
            }
        }
    }
    public List<CommentListItem> getListData(ArrayList<Comment> comments) {
        List<CommentListItem> data = new ArrayList<>();

        int icons = (android.R.drawable.ic_popup_reminder);
        for (int i = 0; i <comments.size(); i++) {
            CommentListItem item = new CommentListItem();
            item.setCommentid(comments.get(i).getCommentid());
            item.setAuthor(comments.get(i).getAuthor());
            item.setContent(comments.get(i).getContent());
            item.setOwner(comments.get(i).getOwner());
            item.setBookid(comments.get(i).getBookid());
            item.setTime(comments.get(i).getTime());
            item.setName(comments.get(i).getName());
            data.add(item);
        }

        return data;
    }
    @Override
    public void onItemClick(int p) {
    }
    @Override
    public void onSecondaryIconClick(int p) {
    }
    public void setRecyclerView()
    {
        adapter=new CommentAdapter(listData,this);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);
    }

}