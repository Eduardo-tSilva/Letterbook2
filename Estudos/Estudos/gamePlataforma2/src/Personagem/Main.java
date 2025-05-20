package Personagem;

import State.Personagem;

public class Main {
    public static void main(String[] args) {

        Personagem p1 = new Personagem("Julia");
        p1.requisitarCorrendo();
        p1.requisitarPulando();
        p1.requisitarEsperando();
        p1.requisitarCorrendo();
        p1.requisitarAbaixando();

    }



}