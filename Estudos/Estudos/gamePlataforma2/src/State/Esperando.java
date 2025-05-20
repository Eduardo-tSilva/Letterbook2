package State;

public class Esperando implements Estado{

    private String nome = "Esperando";
    Personagem personagem;

    public Esperando(Personagem personagem) {
        this.personagem = personagem;
    }

    @Override
    public void abaixando() {
        personagem.setEstado(new Abaixando(personagem));
    }

    @Override
    public void esperando() {
        System.out.println("Esperando...");
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
