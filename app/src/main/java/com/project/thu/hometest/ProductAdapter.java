package com.project.thu.hometest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> productList;
    private String[] arrayColors;
    private OnItemClickListener onItemClickListener;
    private String defaultColor = "#00793b";

    public ProductAdapter(Context context, ArrayList<String> arrayItems, String[] arrayColors, OnItemClickListener listener) {
        this.mContext = context;
        this.productList = arrayItems;
        this.arrayColors = arrayColors;
        this.onItemClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlItem;
        private TextView tvTitle;

        private MyViewHolder(View view) {
            super(view);
            rlItem = (RelativeLayout) view.findViewById(R.id.rlItem);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_product, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        String text = productList.get(position);

        if (!TextUtils.isEmpty(getFormatText(text))) {

            holder.tvTitle.setText(getFormatText(text));
            holder.rlItem.setBackgroundResource(R.drawable.bg_boder);

            GradientDrawable drawable = (GradientDrawable) holder.rlItem.getBackground();
            drawable.setColor(Color.parseColor(arrayColors != null ? arrayColors[getPositionBackground(position, arrayColors.length)] : defaultColor));

            holder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(productList.get(holder.getAdapterPosition()));
                    }
                }
            });

        }
    }

    private String getFormatText(String text) {
        String resultString;
        String resultTempA;
        String resultTempB;
        if (TextUtils.isEmpty(text)) {
            return text;
        } else {
            text = text.trim().replaceAll("\\s+", " ");
        }
        if (count(text, ' ') == 0) {
            resultString = text;
        } else if (count(text, ' ') == 1) {
            String[] arrayTemp = text.split(" ");
            resultTempA = arrayTemp[0];
            resultTempB = arrayTemp[1];
            resultString = resultTempA + "\n" + resultTempB;
        } else {
            int length = text.length();
            int positionBetween = length / 2;
            int prevPosition = positionBetween;
            int nextPosition = positionBetween;
            int bestPosition;
            Character prevCharacter;
            Character nextCharacter;
            boolean isOverArray;
            do {
                boolean isAvailablePrev = false;
                boolean isAvailableNext = false;
                prevCharacter = text.charAt(prevPosition - 1);
                nextCharacter = text.charAt(nextPosition - 1);
                if (prevPosition > 1) {
                    prevPosition--;
                    isAvailablePrev = true;
                }
                if (nextPosition < length - 1) {
                    nextPosition++;
                    isAvailableNext = true;
                }
                isOverArray = !isAvailablePrev && !isAvailableNext;
            }
            while (!isOverArray && !prevCharacter.equals(' ') && !nextCharacter.equals(' '));

            if (prevCharacter.equals(' ')) {
                bestPosition = prevPosition;
                resultTempA = text.substring(0, bestPosition);
                resultTempB = text.substring(bestPosition, length);
                resultString = resultTempA + "\n" + resultTempB;
            } else if (nextCharacter.equals(' ')) {
                bestPosition = nextPosition;
                resultTempA = text.substring(0, bestPosition);
                resultTempB = text.substring(bestPosition, length);
                resultString = resultTempA + "\n" + resultTempB;
            } else {
                resultString = text;
            }
        }
        return resultString;
    }

    private int count(String s, char c) {
        return s.length() == 0 ? 0 : (s.charAt(0) == c ? 1 : 0) + count(s.substring(1), c);
    }

    private int getPositionBackground(int position, int lenghListColor) {
        int positionResult = position;
        while (positionResult >= lenghListColor) {
            positionResult = positionResult - lenghListColor;
        }
        return positionResult;
    }

    /*private int getPositionRandom(int min, int max) {
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        return i1;
        //Note that nextInt(int max) returns an int between 0 inclusive and max exclusive.
    }*/

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String item);
    }
}
