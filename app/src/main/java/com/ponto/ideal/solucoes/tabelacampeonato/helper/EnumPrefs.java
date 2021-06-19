package com.ponto.ideal.solucoes.tabelacampeonato.helper;

public enum EnumPrefs {

    CADASTRAR(1),PESQUISAR(2),EXCLUIR(3);

    private int opcao;

    EnumPrefs(int opcaodefinida) {
        opcao = opcaodefinida;
    }

    public int getOpcao() {
        return opcao;
    }

    public static EnumPrefs prucuraropcao(int id){
        for (EnumPrefs e : values()) {
            if (e.getOpcao() == id)
                return e;
        }
        return null;
    }
}
