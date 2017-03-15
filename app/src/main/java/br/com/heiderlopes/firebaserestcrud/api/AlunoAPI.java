package br.com.heiderlopes.firebaserestcrud.api;


import br.com.heiderlopes.firebaserestcrud.model.Aluno;
import br.com.heiderlopes.firebaserestcrud.model.response.AlunoResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


public interface AlunoAPI {

    @POST("alunos.json")
    Observable<AlunoResponse> add(@Body Aluno aluno);
}
