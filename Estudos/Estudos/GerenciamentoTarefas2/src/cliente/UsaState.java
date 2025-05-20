package cliente;

import state.Tarefa;


/*
*  Professor Gerson Risso
 */
public class UsaState {

    public static void main(String[] args) {
        Tarefa tarefa = Tarefa.getInstancia("Gerson", "Implementar o pattern State com Singleton");
        System.out.println(tarefa.getEstado());
        tarefa.requisitarAtrasada();
        System.out.println(tarefa.getEstado());
        tarefa.requisitarConcluida();
        System.out.println(tarefa.getEstado());
    }

}
