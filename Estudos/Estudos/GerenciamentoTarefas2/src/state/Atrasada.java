package state;

/*
 *  Professor Gerson Risso
 */
public class Atrasada implements Estado {

    private String nome = "Atrasada";
    private Tarefa tarefa;

    public Atrasada(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    @Override
    public void atrasada() {
        System.out.println("Já está atrasada");
    }

    @Override
    public void pendente() {
        System.out.println("Já está atrasada");
    }

    @Override
    public void concluida() {
        tarefa.setEstado(new Concluida(tarefa));
    }

    @Override
    public String toString() {
        return "Atrasada{" + "nome=" + nome + '}';
    }

}
