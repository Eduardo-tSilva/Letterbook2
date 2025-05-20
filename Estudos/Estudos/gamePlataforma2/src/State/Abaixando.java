package State;

public class Abaixando implements Estado{

    private String nome = "Abaixando";
    Personagem personagem;


    public Abaixando(Personagem personagem) {
    }

    @Override
    public void abaixando() {
        System.out.println("Abaixado...");
    }

    @Override
    public void esperando() {
        personagem.setEstado(new Esperando(personagem));
    }

    @Override
    public void pulando() {
        personagem.setEstado(new Pulando(personagem));
    }

    @Override
    public void correndo() {
        personagem.setEstado(new Correndo(personagem));
    }
}
