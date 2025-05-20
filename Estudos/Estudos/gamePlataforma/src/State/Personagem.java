package State;

public class Personagem {

    private String nome;
    private Estado estado = new Esperando(this);

    public Personagem(String nome) {
        this.nome = nome;
    }

    public void requisitarAbaixando(){
        estado.abaixando();
    }
    public void requisitarCorrendo(){
        estado.correndo();
    }
    public void requisitarEsperando(){
        estado.esperando();
    }
    public void requisitarPulando(){
        estado.pulando();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
