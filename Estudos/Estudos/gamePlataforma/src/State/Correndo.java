package State;

public class Correndo implements Estado{

    private String nome = "Correndo";
    Personagem personagem;

    public Correndo(Personagem personagem) {
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
        personagem.setEstado(new Pulando(personagem));
    }

    @Override
    public void correndo() {
        System.out.println("Correndo...");
    }

    @Override
    public String toString() {
        return "Correndo{" + "nome=" + nome +  '}';
    }
    
    
}
