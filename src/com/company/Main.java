package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static int poolSeed[] = new int[3];
    public static int seed = 2;

    public static void getRandSeed(){
        for (int i = 0; i < 3; i++) {
            poolSeed[i] = (int)(Math.random()*20+1);
        }
    }

    public static int getRand(int a, int c, int m) {
        seed = (a * seed + c) % m;
        return seed;
    }

    public static int countPlus(int count){
        return count++;
    }

    public static void main(String[] args) {
        try(FileWriter writer = new FileWriter("notes1.txt", false)) {
            int count = 0;
            getRandSeed();
            System.out.println("-----------------");
            int[] randomSeedValue = new int[10];
            int[] testingRandomSeed = new int[15];
            for (int i = 0; i < 10; i++) {
                randomSeedValue[i] = getRand(poolSeed[0], poolSeed[1], poolSeed[2]);
                if (i > 0) {
                    for (int j = 1; j < 10; j++) {
                        if (randomSeedValue[i] == randomSeedValue[j - 1]) {
                            testingRandomSeed[countPlus(count)] = randomSeedValue[i];
                        }
                    }
                }
            }
            seed = 2;
            System.out.println(randomSeedValue[0]);

            //P - случайное целове число
            //Ca, Da - случайные цисла, которые Ca * Da % (P-1) = 1
            //Cb, Db - случайные цисла, которые Ca * Da % (P-1) = 1
            //m - сообщение. m < P то сразу, если m >= P, то m становится m1,m2,m3,mN, где все m < P
            //x1 = m^Ca % P
            //x2 = x1^Cb % P
            //x3 = x2^Da % P
            //x4 = x3^Db % P
            //В итоге x4 = m
            int p = randomSeedValue[0];
            int Ca = 0, Da = 0, Cb = 0, Db = 0;
            boolean falseCAndD = true;
            boolean falseP = true;

            int m;
            System.out.println("Are you want enter key?");
            Scanner scanner = new Scanner(System.in);
            String userValue = scanner.nextLine();
            System.out.println(userValue == "+");
            if(userValue.equals("Yes") || userValue.equals("+") || userValue.equals("yes")){
                System.out.println("Please enter key?");
                m = scanner.nextInt();
            }else{
                System.out.println(userValue);
                m = 4 + 26;
            }

            int lenghtArray = 10;
            int[] mArray = new int[lenghtArray];
            BigInteger singleValue = BigInteger.valueOf(p);
            while (falseP){
                if((p - 1) == 0 || !singleValue.isProbablePrime((int)Math.log(p)) || p <= 0){
                    getRandSeed();
                    p = getRand(poolSeed[0], poolSeed[1], poolSeed[2]);
                    singleValue = BigInteger.valueOf(p);
                }else falseP = false;
            }

            System.out.println(p);
            Thread.sleep(5000);
            while (falseCAndD){
                getRandSeed();
                Ca = getRand(poolSeed[0], poolSeed[1], poolSeed[2]);
                Da = getRand(poolSeed[0], poolSeed[1], poolSeed[2]);
                System.out.println("Ca = " + Ca + ", Da = " + Da);
                if(((Ca * Da) % (p - 1)) == 1) {
                    falseCAndD = false;
                }
            }

            falseCAndD = true;
            while (falseCAndD){
                getRandSeed();
                Cb = getRand(poolSeed[0], poolSeed[1], poolSeed[2]);
                Db = getRand(poolSeed[0], poolSeed[1], poolSeed[2]);
                System.out.println("Cb = " + Cb + ", Db = " + Db);
                if(((Cb * Db) % (p - 1)) == 1) {
                    falseCAndD = false;
                }
            }
            System.out.println("Finish: Ca = " + Ca + ", Da = " + Da + ", p = " + p);
            System.out.println("Finish: Cb = " + Cb + ", Db = " + Db + ", p = " + p);

            boolean trueWhile = true;
            int mArrayCount = 0;
            for (int i = 0; trueWhile; i++) {
                if((m-i) < p){
                    mArray[mArrayCount] = m - i;
                    m = i;
                    i = 0;
                    mArrayCount++;
                }
                if(mArrayCount == lenghtArray){
                    trueWhile = false;
                }
            }
            mArray = Arrays.copyOf(mArray, lenghtArray + 1);
            mArray[lenghtArray] = 1;

            int countArrayTrue = 0;


            for (int i = 0; i < mArray.length; i++) {
                countArrayTrue++;
                if(mArray[i] == 0){
                    mArray[i] = mArray[lenghtArray];
                    System.out.println("I[" + i + "] = " + mArray[i]);
                    mArray[lenghtArray] = 0;
                    i = mArray.length - 1;
                }
                System.out.println("I[" + i + "] = " + mArray[i]);
            }


            System.out.println("Count = " + countArrayTrue);


            //x1 = m^Ca % P
            //x2 = x1^Cb % P
            //x3 = x2^Da % P
            //x4 = x3^Db % P
            int[] xArray = new int[mArray.length];
            int x1 = 0, x2 = 0, x3 = 0, x4 = 0;
            for (int i = 0; i < mArray.length; i++) {
                x1 = (int)Math.pow(mArray[i], Ca) % p;
                x2 = (int)Math.pow(x1, Cb) % p;
                x3 = (int)Math.pow(x2, Da) % p;
                x4 = (int)Math.pow(x3, Db) % p;
                xArray[i] = x4;
            }
            xArray = Arrays.copyOf(xArray, countArrayTrue + 1);
            mArray[countArrayTrue] = 1;
            for (int i = 0; i < countArrayTrue; i++) {
                if(xArray[i] == 0){
                    xArray[i] = xArray[countArrayTrue];
                    System.out.println("X[" + i + "] = " + xArray[i]);
                    xArray[countArrayTrue] = 0;
                    i = xArray.length - 1;
                }
                System.out.println("X[" + i + "] = " + xArray[i]);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Enter correct value!");
        }

//        System.out.println("Testing:");
//        for (int i = 0; i < 15; i++) {
//            System.out.println(testingRandomSeed[i]);
//        }
    }
}
