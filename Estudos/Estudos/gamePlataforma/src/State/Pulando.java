package State;

public class Pulando implements Estado{

    private String nome = "Pulando";
    Personagem personagem;

    public Pulando(Personagem personagem) {
        this.personagem = personagem;
    }

    @Override
    public void abaixando() {
        personagem.setEstado(new Abaixando(personagem));
    }

    @Override
    public void esperando() {
        personagem.setEstado(new Esperando(personagem));
    }

    @Override
    public void pulando() {
        System.out.println("Pulando...");
    }

    @Override
    public void correndo() {
        personagem.setEstado(new Correndo(personagem));
    }

    @Override
    public String toString() {
        return "Pulando{" + "nome=" + nome +'}';
    }
    
    
}
