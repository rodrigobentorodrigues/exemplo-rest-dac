package com.ifpb.ws.infra;

import com.ifpb.ws.entidades.Pessoa;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@Local(PessoaDao.class)
public class PessoaDaoImpl implements PessoaDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void add(Pessoa p) {
        em.persist(p);
    }

    @Override
    public void remove(Pessoa p) {
        Pessoa aux = em.find(Pessoa.class, p.getId());
        em.remove(p);
    }

    @Override
    public void update(Pessoa p) {
        em.merge(p);
    }

    @Override
    public List<Pessoa> listarTodos() {
        return em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();
    }
    
    @Override
    public Optional<Pessoa> buscarPessoa(String nome){
        TypedQuery<Pessoa> query = em.createQuery("SELECT p FROM Pessoa p WHERE p.nome = :nome", Pessoa.class);
        query.setParameter("nome", nome);
        List<Pessoa> pessoas = query.getResultList();
        Optional<Pessoa> pessoa = pessoas.stream().findFirst();
        return pessoa;
    }
    
}
