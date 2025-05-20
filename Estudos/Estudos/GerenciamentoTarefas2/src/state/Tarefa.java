package state;

/*
 *  Professor Gerson Risso
 */
public class Tarefa {

    private static Tarefa instancia; 
    private String nome;
    private String descricao;
    private Estado estado = new Pendente(this);
    private Tarefa(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
    public static Tarefa getInstancia(String nome, String descricao) {
        if (instancia == null) {
            instancia = new Tarefa(nome, descricao);
        }
        return instancia;
    }
    public void requisitarAtrasada() {
        estado.atrasada();
    }
    public void requisitarConcluida() {
        estado.concluida();
    }
    public void requisitarPendente() {
        estado.pendente();
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    @Override
    public String toString() {
        return "Tarefa{" + "estado=" + estado + '}';
    }
}
