package io.github.carlosacavalcante;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.carlosacavalcante.entity.Cliente;
import io.github.carlosacavalcante.repository.Clientes;


@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes){
        return args ->{
            System.out.println("Cadastrar Cliente");
            Cliente cliente =  new Cliente();
            cliente.setNome("Maria");
            clientes.salvar(cliente);

            cliente.setNome("Jose");
            clientes.salvar(cliente);

            cliente.setNome("Joao");
            clientes.salvar(cliente);

            System.out.println("Buscar todos");
            List<Cliente> todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizar todos");
            todosClientes.forEach( c -> {
                c.setNome(c.getNome() + " Atualizado");
                clientes.atualizar(c);
            });

            todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Buscando por nome");
            todosClientes = clientes.obterPorNome("Ma");
            todosClientes.forEach(System.out::println);

            System.out.println("Deletar por id");
            clientes.deletar(2);

            todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }




}
