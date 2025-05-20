package state;

/*
 *  Professor Gerson Risso
 */
public class Pendente implements Estado{
    private String nome="Pendente";
    private Tarefa tarefa;
            
    public Pendente(Tarefa tarefa){
      this.tarefa=tarefa;
    }
    
    @Override
    public void atrasada() {
        tarefa.setEstado(new Atrasada(tarefa));
    }

    @Override
    public void pendente() {
        System.out.println("Já está pendente");
    }

    @Override
    public void concluida() {
        tarefa.setEstado(new Concluida(tarefa));
    }

    @Override
    public String toString() {
        return "Pendente{" + "nome=" + nome + '}';
    }

    
}
