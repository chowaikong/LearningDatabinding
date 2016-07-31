package me.knox.learningdatabinding;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import me.knox.learningdatabinding.databinding.ItemRvBinding;

/**
 * Created by KNOX on 7/31/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<DataBoundViewHolder<ItemRvBinding>> {

  private List<User> users;

  public RecyclerViewAdapter(List<User> users) {
    this.users = users;
  }

  @Override
  public DataBoundViewHolder<ItemRvBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
    ItemRvBinding binding = ItemRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new DataBoundViewHolder<>(binding);
  }

  @Override public void onBindViewHolder(DataBoundViewHolder<ItemRvBinding> holder, int position) {
    holder.getBinding().setUser(users.get(position));
    holder.getBinding().executePendingBindings();
  }

  @Override public int getItemCount() {
    return users == null ? 0 : users.size();
  }
}
