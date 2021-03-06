package com.quickben22.bitcoinlotto.fragments;
import java.lang.Object;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.content.ClipboardManager;
import android.widget.TextView;
import android.content.ClipData;

import com.google.android.gms.analytics.HitBuilders;
import com.quickben22.bitcoinlotto.CryptoClass;
import com.quickben22.bitcoinlotto.R;
import com.quickben22.bitcoinlotto.databinding.FragmentTwoBinding;




public class TwoFragment extends Fragment{

    public TwoFragment() {
        // Required empty public constructor
    }

    TextView input_text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentTwoBinding binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_two, container, false);
    binding.setKeysD(CryptoClass.keysD);
   View  view = binding.getRoot();


//         View view = inflater.inflate(R.layout.fragment_two,
//                container, false);


    Button private_input_button = (Button) view.findViewById(R.id.private_button);
    final TextView mSwitcher = (TextView) view.findViewById(R.id.error3);
    final TextView mSwitcher2 = (TextView) view.findViewById(R.id.error5);


    private_input_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("Input private")
                    .build());

//                TextView private_tb = (TextView)  getView().findViewById(R.id.private_tb);
//                EditText mEditText = (EditText)  getView().findViewById(R.id.private_tx);
            String message = CryptoClass.GetPrivateKey(CryptoClass.keysD.getInputKey());
            boolean isHex = message.toUpperCase().matches("[0-9A-F]+");
            if (message.length() == 64 && isHex) {

                CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(message, " ", 2));
            } else {
                mSwitcher.setVisibility(View.VISIBLE);
                fadeOutAndHideImage(mSwitcher);

            }


        }
    });

    Button random_button = (Button) view.findViewById(R.id.random_button);

    random_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("Random Click")
                    .build());

            String message = CryptoClass.random();
            CryptoClass.keysD.setPrivateKey(CryptoClass.insertPeriodically(message, " ", 2));
            CryptoClass.keysD.setInputKey(message);


        }
    });


    Button private_public_button = (Button) view.findViewById(R.id.private_public_button);

    private_public_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("Private to public")
                    .build());

            String PrivText = CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey());

            if (PrivText.length() != 64) return;
            byte[] hex = CryptoClass.hexStringToByteArray(PrivText);


            String[] message = CryptoClass.generateAddresses(hex);

            CryptoClass.keysD.setPublicKey_compressed(message[0]);
            CryptoClass.keysD.setPublicKey_uncompressed(message[1]);

            TextView copy_button1 = getView().findViewById(R.id.copy_button1);
            TextView copy_button2 = getView().findViewById(R.id.copy_button2);
            copy_button1.setVisibility(View.VISIBLE);
            copy_button2.setVisibility(View.VISIBLE);
//                TextView textView = getView().findViewById(R.id.public_tb);
//                textView.setText(message[0]+"\n"+message[1]);


        }
    });


    Button private_WIF_button = (Button) view.findViewById(R.id.private_wif_button);

    private_WIF_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("Private to WIF")
                    .build());

            String PrivText = CryptoClass.remove_extra(CryptoClass.keysD.getPrivateKey());

            if (PrivText.length() != 64) return;
            byte[] hex = CryptoClass.hexStringToByteArray(PrivText);


            String[] message = CryptoClass.GeneratePrivHexToWIF(hex);

            CryptoClass.keysD.setPublicKey_compressed(CryptoClass.insertPeriodically(message[0], "", 1));
            CryptoClass.keysD.setPublicKey_uncompressed(CryptoClass.insertPeriodically(message[1], "", 1));

            TextView copy_button1 = getView().findViewById(R.id.copy_button1);
            TextView copy_button2 = getView().findViewById(R.id.copy_button2);
            copy_button1.setVisibility(View.VISIBLE);
            copy_button2.setVisibility(View.VISIBLE);
//                TextView textView = getView().findViewById(R.id.public_tb);
//                textView.setText(message[0]+"\n"+message[1]);


        }
    });


    Button copy_button1 = (Button) view.findViewById(R.id.copy_button1);

    copy_button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("Copy public 1")
                    .build());

            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Public key compressed", CryptoClass.remove_extra(CryptoClass.keysD.getPublicKey_compressed()));
            clipboard.setPrimaryClip(clip);
            mSwitcher2.setVisibility(View.VISIBLE);
            fadeOutAndHideImage(mSwitcher2);

        }
    });


    Button copy_button2 = (Button) view.findViewById(R.id.copy_button2);

    copy_button2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("Copy public 2")
                    .build());

            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Public key uncompressed", CryptoClass.remove_extra(CryptoClass.keysD.getPublicKey_uncompressed()));
            clipboard.setPrimaryClip(clip);
            mSwitcher2.setVisibility(View.VISIBLE);
            fadeOutAndHideImage(mSwitcher2);

        }
    });


    Button info_button = (Button) view.findViewById(R.id.wif_info_button);
    info_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            CryptoClass.mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("WIF info")
                    .build());

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Accessing bitcoins?");
            alertDialog.setMessage("How to access a lost bitcoin the app finds? \n 1. First you need to click the 'Wallet Key' button, which generates the 'Wallet import format' key (WIF)." +
                    "\n 2. Install a bitcoin wallet app (for example Mycelium wallet)." +
                    "\n 3. Add a new account based on a private key to the wallet. " +
                    "\n 4. Copy one of the generated WIF keys and insert them into the account made in step 3.  " +
                    "\n That's it! Now you can access the bitcoins.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();


        }
    });


    EditText meditext = (EditText) view.findViewById(R.id.private_tx);
    meditext.addTextChangedListener(mTextEditorWatcher);
    input_text = view.findViewById(R.id.LetterCounter);

       return view;
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length

            input_text.setText (String.valueOf(CryptoClass.keysD.getInputKey().length()));
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void fadeOutAndHideImage(final TextView img)
    {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(3000);

        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                img.setVisibility(View.GONE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        img.startAnimation(fadeOut);
    }




}
