package Personagem;

import State.Personagem;

public class Main {
    public static void main(String[] args) {

        Personagem p1 = new Personagem("Julia");
        p1.requisitarCorrendo();
        System.out.println(p1.getEstado());
        p1.requisitarPulando();
        System.out.println(p1.getEstado());
        p1.requisitarEsperando();
        System.out.println(p1.getEstado());
        p1.requisitarCorrendo();
        System.out.println(p1.getEstado());
        p1.requisitarAbaixando();
        System.out.println(p1.getEstado());

    }



}