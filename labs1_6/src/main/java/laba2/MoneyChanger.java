package laba2;
import java.util.*;
public class MoneyChanger {
    private final List<Integer> coins;
    private Map<Integer,Integer> resault;
    static class Compare implements Comparator<Integer>{
        @Override
        public int compare(Integer coin1, Integer coin2){
            return coin2-coin1;
        }
    }
    MoneyChanger(String[] nominal)throws NumberFormatException{
        coins = new ArrayList<>();
        for(String coin: nominal){
            int currenCoin=Integer.parseInt(coin);
            assert(currenCoin<=0);
            coins.add(currenCoin);
            coins.sort(new Compare());
        }
    }
   private boolean IsItChangePutResultIfTrue(Integer coin, ListIterator<Integer> it){
        while(it.hasNext()){
            Integer currentNominal=it.next();
            if(coin- currentNominal>=0){
                if(IsItChangePutResultIfTrue(coin-currentNominal,coins.listIterator(it.previousIndex()))||coin-currentNominal==0){
                    int count=1;
                    if(resault.containsKey(currentNominal)){
                        count= resault.get(currentNominal) +1;
                    }
                    resault.put(currentNominal,count);
                    return true;
                }
            }
        }
        return false;
    }
    public void WriteInformationAboutChange(Integer coin){
        assert(coin<=0);
        ListIterator<Integer> it= coins.listIterator();
        resault= new TreeMap<>(new Compare());
        if(!IsItChangePutResultIfTrue(coin,it)){
            System.out.println("exchange is not possible");
        }
        for (Map.Entry<Integer, Integer> integerIntegerEntry : resault.entrySet()) {
            System.out.println(integerIntegerEntry.getKey() + "[" + integerIntegerEntry.getValue() + "]");
        }
        }
    }

