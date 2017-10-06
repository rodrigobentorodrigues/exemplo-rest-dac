package com.ifpb.ws.path;

import com.ifpb.ws.entidades.Pessoa;
import com.ifpb.ws.infra.PessoaDao;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("pessoa")
@Stateless
public class PessoaResource {

    @Inject
    private PessoaDao dao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retornarPessoas() {
        GenericEntity<List<Pessoa>> pessoas = new GenericEntity<List<Pessoa>>(dao.listarTodos()) {
        };
        return Response.ok().entity(pessoas).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPessoa(Pessoa p) {
        dao.add(p);
        return Response.ok().entity(p).build();
    }

    @GET
    @Path("{nome}")
    public Response buscarPessoa(@PathParam("nome") String nome) {
        Pessoa p = dao.buscarPessoa(nome).orElse(new Pessoa());
        return Response.ok().entity(p).build();
    }

    @DELETE
    @Path("{nome}")
    public Response deletarPessoa(@PathParam("nome") String nome) {
        Optional<Pessoa> buscarPessoa = dao.buscarPessoa(nome);
        if (buscarPessoa.isPresent()) {
            Pessoa p = buscarPessoa.get();
            dao.remove(p);
            return Response.ok().entity(p).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{nome}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarPessa(@PathParam("nome") String nome, Pessoa pessoa) {
        Optional<Pessoa> buscarPessoa = dao.buscarPessoa(nome);
        if (buscarPessoa.isPresent()) {
            Pessoa p = buscarPessoa.get();
            p.setNome(pessoa.getNome());
            p.setIdade(pessoa.getIdade());
            dao.update(p);
            return Response.ok().entity(p).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
