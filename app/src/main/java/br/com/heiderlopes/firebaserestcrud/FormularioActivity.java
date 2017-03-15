package br.com.heiderlopes.firebaserestcrud;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.google.gson.GsonBuilder;
import br.com.heiderlopes.firebaserestcrud.api.AlunoAPI;
import br.com.heiderlopes.firebaserestcrud.model.Aluno;
import br.com.heiderlopes.firebaserestcrud.model.response.AlunoResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class FormularioActivity extends AppCompatActivity {


    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRetrofit();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {

        final AlunoAPI alunoAPI =
                retrofit.create(AlunoAPI.class);

        Aluno novoAluno = new Aluno();
        novoAluno.setNome("Testes");
        novoAluno.setEmail("teste@teste.com.br");

        alunoAPI.add(novoAluno)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(FormularioActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .subscribe(new Observer<AlunoResponse>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(FormularioActivity.this, "Inserido com sucesso!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(FormularioActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(AlunoResponse alunoResponse) {

                    }
                });

    }

    private void initRetrofit() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://demobaas-61559.firebaseio.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();
    }

}
