package com.pokemeows.pokipoki.tools.database.models;

/**
 * Created by alexisjouhault on 7/17/16.
 *
 */
public class CardOptions {
    public final static int FAVOURITE = 1;
    public final static int HAVE = 2;
    public final static int WANT = 4;

    public static boolean isOptionSelected(int cardOptions, int option) {
        switch (cardOptions) {
            case 0:
                break;
            case CardOptions.FAVOURITE:
                if (option == CardOptions.FAVOURITE) {
                    return true;
                }
                break;
            case CardOptions.HAVE:
                if (option == CardOptions.HAVE) {
                    return true;
                }
                break;
            case CardOptions.FAVOURITE + CardOptions.HAVE:
                if (option == CardOptions.FAVOURITE || option == CardOptions.HAVE) {
                    return true;
                }
                break;
            case CardOptions.WANT:
                if (option == CardOptions.WANT) {
                    return true;
                }
                break;
            case CardOptions.FAVOURITE + CardOptions.WANT:
                if (option == CardOptions.FAVOURITE || option == CardOptions.WANT) {
                    return true;
                }
                break;
            case CardOptions.HAVE + CardOptions.WANT:
                if (option == CardOptions.HAVE || option == CardOptions.WANT) {
                    return true;
                }
                break;
            case CardOptions.FAVOURITE + CardOptions.HAVE + CardOptions.WANT:
                if (option == CardOptions.FAVOURITE || option == CardOptions.HAVE
                        || option == CardOptions.WANT) {
                    return true;
                }
                break;
        }
        return false;
    }
}
