package bf.esta.kasa;
//Step 10: Creating a RecyclerView Adapter class to set data for each item of RecyclerView
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
public class ContactAdapter extends ListAdapter<Contact, ContactAdapter.ViewHolder>{
    // creating a variable for on item click listener.
    private OnItemClickListener listener;

    // creating a constructor class for our adapter class.
    ContactAdapter() {
        super(DIFF_CALLBACK);
    }

    // creating a call back for item of recycler view.
    private static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(Contact oldItem, Contact newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Contact oldItem, Contact newItem) {
            // below line is to check the course name, description and course duration.
            return oldItem.getContact_nom().equals(newItem.getContact_nom()) &&
                    oldItem.getContact_prenom().equals(newItem.getContact_prenom()) &&
                    Integer.toString(oldItem.getContact_tel()).equals(Integer.toString(newItem.getContact_tel())) &&
                    oldItem.getContact_desc().equals(newItem.getContact_desc()) && oldItem.getContact_image().equals(newItem.getContact_image());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is use to inflate our layout
        // file for each item of our recycler view.
        //View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // below line of code is use to set data to
        // each item of our recycler view.
        Contact model = getContactAt(position);
        holder.contactNomTV.setText(model.getContact_nom());
        holder.contactPrenomTV.setText(model.getContact_prenom());
        holder.contactTelTV.setText(Integer.toString(model.getContact_tel()));
//        holder.contactDescTV.setText(model.getContact_desc());
        holder.contactImageIV.setImageURI(Uri.parse(model.getContact_image()));
    }

    // creating a method to get course modal for a specific position.
    public Contact getContactAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // view holder class to create a variable for each view.
        TextView contactNomTV, contactPrenomTV, contactTelTV,contactDescTV;
        ImageView contactImageIV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            contactNomTV = itemView.findViewById(R.id.contactname_id);
            contactPrenomTV = itemView.findViewById(R.id.contactprenom_id);
            contactTelTV = itemView.findViewById(R.id.contacttel_id);
            contactDescTV = itemView.findViewById(R.id.contactdesc_id);
            contactImageIV=itemView.findViewById(R.id.imageView);

            // adding on click listener for each item of recycler view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // inside on click listener we are passing
                    // position to our item of recycler view.
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
   /* @Override
    public int getItemCount() {
        return getCurrentList().size();
    }*/

    public interface OnItemClickListener {
        void onItemClick(Contact model);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
