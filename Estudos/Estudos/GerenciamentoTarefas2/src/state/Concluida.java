package state;

/*
 *  Professor Gerson Risso
 */
public class Concluida implements Estado{
    private String nome = "Concluida";
    private Tarefa tarefa;

    public Concluida(Tarefa tarefa) {
        this.tarefa = tarefa;
    }
    
    @Override
    public void atrasada() {
        System.out.println("Já está concluída");
    }

    @Override
    public void pendente() {
        tarefa.setEstado(new Pendente(tarefa));
    }

    @Override
    public void concluida() {
         System.out.println("Já está concluída");
    }

    @Override
    public String toString() {
        return "Concluida{" + "nome=" + nome + '}';
    }

}
