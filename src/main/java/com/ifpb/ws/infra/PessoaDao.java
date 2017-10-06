package com.ifpb.ws.infra;

import com.ifpb.ws.entidades.Pessoa;
import java.util.List;
import java.util.Optional;

public interface PessoaDao {
    
    void add(Pessoa p);
    void remove(Pessoa p);
    void update(Pessoa p);
    Optional<Pessoa> buscarPessoa(String nome);
    List<Pessoa> listarTodos();
    
}
