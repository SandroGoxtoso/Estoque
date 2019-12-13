package br.com.SandroDiego.MenuPrincipalEstoque;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemPedido extends RecyclerView.Adapter<ItemPedido.MyViewHolder> {

    private static List<Produto> listaProdutos;
    private Context context;

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
        holder.tv_valorProduto.setText("R$ " + listaProdutos.get(position).decimalFormat(listaProdutos.get(position).getSomaProduto()));
        holder.tv_qtdProduto.setText(listaProdutos.get(position).getQtdProduto());
        holder.tv_codigoBarra.setText("Código de barra: " + listaProdutos.get(position).getCodigoBarra());
        holder.img_imagemProduto.setImageResource(listaProdutos.get(position).getImgProduto());

        holder.cv_itensPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddProdutosActivity.class);
                intent.putExtra("NomeProduto", listaProdutos.get(position).getNomeProduto());
                intent.putExtra("ValorProduto", listaProdutos.get(position).getValorProduto());
                intent.putExtra("ValorUnitarioProduto", listaProdutos.get(position).getValorProduto());
                intent.putExtra("QtdProduto", listaProdutos.get(position).getQtdProduto());
                intent.putExtra("ImagemProduto", listaProdutos.get(position).getImgProduto());
                intent.putExtra("CodigoBarra", listaProdutos.get(position).getCodigoBarra());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    // Ao extender a classe asbtrata RecyclerView.ViewHolder é nescessário implementar seu método abstrato MyViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nomeProduto, tv_valorProduto, tv_valorUnitarioProduto, tv_qtdProduto, tv_valorTotalProduto, tv_codigoBarra;
        ImageView img_imagemProduto;
        CardView cv_itensPedido;

        // Método da classe abstrata ViewHolder
        public MyViewHolder(View itemView) {
            super(itemView);
            // Atribuição
            tv_nomeProduto = itemView.findViewById(R.id.tv_nomeProduto);
            tv_valorProduto = itemView.findViewById(R.id.tv_valorProduto);
            tv_valorUnitarioProduto = itemView.findViewById(R.id.tv_valorUnitarioProduto);
            tv_qtdProduto = itemView.findViewById(R.id.tv_qtdProduto);
            img_imagemProduto = itemView.findViewById(R.id.img_imagemProduto);
            cv_itensPedido = itemView.findViewById(R.id.cv_item_produto);
            tv_valorTotalProduto = itemView.findViewById(R.id.tv_valorTotalPedido);
            tv_codigoBarra = itemView.findViewById(R.id.tv_codigoBarra);
            cv_itensPedido.setTranslationX(800);
            cv_itensPedido.setAlpha(0);
            cv_itensPedido.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        }
    }
}
