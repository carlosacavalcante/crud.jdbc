package io.github.carlosacavalcante.repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import io.github.carlosacavalcante.entity.Cliente;

@Repository
public class Clientes {

    private static String insert = "insert into cliente (nome) values (?)";
    private static String selectAll = "SELECT * FROM CLIENTE";
    private static String update = "update cliente set nome = ? where id = ?";
    private static String delete = "delete from cliente where id = ?";


    private JdbcTemplate jdbcTemplate;

    public Clientes(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Cliente salvar(Cliente cliente){
        jdbcTemplate.update(insert, new Object[]{cliente.getNome()});
        return cliente;
    }

    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update(update, new Object[]{
            cliente.getNome(),
            cliente.getId()
        });
        return cliente;
    }

    public void deletar(Cliente cliente){
        deletar(cliente.getId());
    }

    public void deletar(Integer id){
        jdbcTemplate.update(delete, new Object[]{id});
    }

    @SuppressWarnings("deprecation")
    public List<Cliente> obterPorNome(String nome){
        return jdbcTemplate.query(selectAll.concat(" where nome like ?"), new Object[]{"%" + nome + "%"}, obterClienteMapper());
    }


    
    public List<Cliente> obterTodos(){
        return jdbcTemplate.query(selectAll, obterClienteMapper());  

    }

    private RowMapper<Cliente> obterClienteMapper(){
       return new RowMapper<Cliente>() {
        @Override
            public Cliente mapRow(ResultSet resulSet, int i) throws SQLException{
                Integer id = resulSet.getInt("id");
                String nome = resulSet.getString("nome");
                return new Cliente(id, nome);
            }
       }; 
        
    }
}
