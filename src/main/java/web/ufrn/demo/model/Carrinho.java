package web.ufrn.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private final List<Produtos> produtos;

    public Carrinho() {
        produtos = new ArrayList<>();
    }

	public void adiciona(Produtos p) {
        produtos.add(p);
    }

    public float getTotal() {
        float total = 0;
        for (Produtos p : produtos) {
            total += p.getPreco();

        }
        return total;
    }


}
