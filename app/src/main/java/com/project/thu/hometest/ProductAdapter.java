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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> productList;
    private String[] arrayColors;

    public ProductAdapter(Context context, ArrayList<String> arrayItems, String[] arrayColors) {
        this.mContext = context;
        this.productList = arrayItems;
        this.arrayColors = arrayColors;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llItem;
        private TextView tvTitle;

        public MyViewHolder(View view) {
            super(view);
            llItem = (LinearLayout) view.findViewById(R.id.llItem);
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String text = productList.get(position);
        
        if (!TextUtils.isEmpty(getFormatText(text))) {

            holder.tvTitle.setText(getFormatText(text));
            holder.llItem.setBackgroundResource(R.drawable.bg_boder);

            final GradientDrawable drawable = (GradientDrawable) holder.llItem.getBackground();

            drawable.setColor(Color.parseColor(arrayColors[getPositionBackground(position, arrayColors.length)]));

            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    String list = productList.get(position);
                    Toast.makeText(mContext, list, Toast.LENGTH_SHORT).show();
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
                prevCharacter = new Character(text.charAt(prevPosition - 1));
                nextCharacter = new Character(text.charAt(nextPosition - 1));
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

    public static int count(String s, char c) {
        return s.length() == 0 ? 0 : (s.charAt(0) == c ? 1 : 0) + count(s.substring(1), c);
    }

    public static String[] split(String src, int len) {
        String[] result = new String[(int) Math.ceil((double) src.length() / (double) len)];
        for (int i = 0; i < result.length; i++)
            result[i] = src.substring(i * len, Math.min(src.length(), (i + 1) * len));
        return result;
    }

    public static String[] splitByNumber(String str, int size) {
        return (size < 1 || str == null) ? null : str.split("(?<=\\G.{" + size + "})");
    }

    private int getPositionRandom(int min, int max) {
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        return i1;
        //Note that nextInt(int max) returns an int between 0 inclusive and max exclusive.
    }

    private int getPositionBackground(int position, int lenghListColor) {
        int positionResult = position;
        while (positionResult >= lenghListColor) {
            positionResult = positionResult - lenghListColor;
        }
        return positionResult;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }
}
