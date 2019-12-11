package br.com.SandroDiego.MenuPrincipalEstoque;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemPedido extends RecyclerView.Adapter<ItemPedido.MyViewHolder> {

    private Context context;
    private List<Produto> listaProdutos;

    public ItemPedido(Context context, List<Produto> listaJogos) {
        this.context = context;
        this.listaProdutos = listaJogos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.itens_pedido, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_nomeProduto.setText(listaProdutos.get(position).getNomeProduto());
        holder.tv_valorProduto.setText("R$ " + String.valueOf(listaProdutos.get(position).getSomaProduto()));
        holder.tv_qtdProduto.setText(listaProdutos.get(position).getQtdProduto());
        holder.img_imagemProduto.setImageResource(listaProdutos.get(position).getImgProduto());

        holder.cv_itensPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, CarrinhoProduto.class);

                // Salva os valores das intentes por indice na Tela do Carrinho
                intent.putExtra("NomeProduto", listaProdutos.get(position).getNomeProduto());
                intent.putExtra("ValorProduto", listaProdutos.get(position).getValorProduto());
                intent.putExtra("QtdProduto", listaProdutos.get(position).getQtdProduto());
                intent.putExtra("ImagemProduto", listaProdutos.get(position).getImgProduto());
                // Salva os valores das intentes por indice na Tela Detalhes Jogos
                intent.putExtra("NomeProduto", listaProdutos.get(position).getNomeProduto());
                intent.putExtra("ValorProduto", listaProdutos.get(position).getValorProduto());
                intent.putExtra("QtdProduto", listaProdutos.get(position).getQtdProduto());
                intent.putExtra("ImagemProduto", listaProdutos.get(position).getImgProduto());

                // Inicia a activity
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    // Ao extender a classe asbtrata RecyclerView.ViewHolder é nescessário implementar seu método abstrato MyViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nomeProduto, tv_valorProduto, tv_qtdProduto, tv_valorTotalProduto;
        ImageView img_imagemProduto;
        CardView cv_itensPedido;

        // Método da classe abstrata ViewHolder
        public MyViewHolder(View itemView) {
            super(itemView);
            // Atribuição
            tv_nomeProduto = itemView.findViewById(R.id.tv_nomeProduto);
            tv_valorProduto = itemView.findViewById(R.id.tv_valorProduto);
            tv_qtdProduto = itemView.findViewById(R.id.tv_qtdProduto);
            img_imagemProduto = itemView.findViewById(R.id.img_imagemProduto);
            cv_itensPedido = itemView.findViewById(R.id.cv_item_produto);
            tv_valorTotalProduto = itemView.findViewById(R.id.tv_valorTotalPedido);
        }
    }
}
