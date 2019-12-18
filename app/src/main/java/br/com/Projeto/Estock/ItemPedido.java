package br.com.Projeto.Estock;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemPedido extends RecyclerView.Adapter<ItemPedido.MyViewHolder> implements Filterable {

    private static List<Produto> listaProdutos;
    private static List<Produto> listaProdutosFiltrado;
    private Context context;

    public ItemPedido(Context context, List<Produto> listaJogos) {
        this.context = context;
        this.listaProdutos = listaJogos;
        this.listaProdutosFiltrado = listaJogos;
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
        holder.cv_itensPedido.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.tv_nomeProduto.setText(listaProdutosFiltrado.get(position).getNomeProduto());
        holder.tv_valorProduto.setText("R$ " + listaProdutosFiltrado.get(position).decimalFormat(listaProdutos.get(position).getSomaProduto()));
        holder.tv_qtdProduto.setText("" + listaProdutosFiltrado.get(position).getQtdProduto());
        holder.tv_codigoBarra.setText("Código de barra: " + listaProdutosFiltrado.get(position).getCodigoBarra());
        holder.img_imagemProduto.setImageResource(listaProdutosFiltrado.get(position).getImgProduto());
        holder.cv_itensPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddProdutosActivity.class);
                intent.putExtra("NomeProduto", listaProdutosFiltrado.get(position).getNomeProduto());
                intent.putExtra("ValorProduto", listaProdutosFiltrado.get(position).getValorProduto());
                intent.putExtra("ValorUnitarioProduto", listaProdutosFiltrado.get(position).getValorProduto());
                intent.putExtra("QtdProduto", listaProdutosFiltrado.get(position).getQtdProduto());
                intent.putExtra("ImagemProduto", listaProdutosFiltrado.get(position).getImgProduto());
                intent.putExtra("CodigoBarra", listaProdutosFiltrado.get(position).getCodigoBarra());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProdutosFiltrado.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String Key = charSequence.toString();
                if (Key.isEmpty()) {
                    listaProdutosFiltrado = listaProdutos;
                } else {
                    List<Produto> filtrados = new ArrayList<>();
                    for (Produto produto : listaProdutos) {
                        if (produto.getNomeProduto().toLowerCase().contains(Key.toLowerCase())) {
                            filtrados.add(produto);
                        }
                    }
                    listaProdutosFiltrado = filtrados;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listaProdutosFiltrado;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listaProdutosFiltrado = (List<Produto>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
        }
    }
}