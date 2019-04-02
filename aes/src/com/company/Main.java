package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
public static byte[] cipher;

    public static void main(String[] args) {
//	if(args[0].equals("-e"))
//	    encryptInput(args);
//	else if(args[0].equals("-d"))
//	    decryptInput(args);
//	else if(args[0].equals("-b"))
//	    breakEncrypt(args);
//
//	else
//        System.out.println("Wrong input...\nPlease try again");
	String[] cipherikos = new String[7];
	cipherikos[2] = "d:\\documents\\users\\dombo\\Downloads\\AES3_test_files\\key_short";
	cipherikos[4] =	"d:\\documents\\users\\dombo\\Downloads\\AES3_test_files\\message_short";
	cipherikos[6] = "d:\\documents\\users\\dombo\\Downloads\\AES3_test_files\\output";
	encryptInput(cipherikos);
    }


    private static void breakEncrypt(String[] args) {

	}

	private static void decryptInput(String[] args) {

	}

	private static void encryptInput(String[] args) {
    	String keysFile = args[2];
    	String inputFile = args[4];
    	String outputFile = args[6];
		byte[] keys = null;
		byte[] message=null;
		int messageLength=0,rowBlockIndex=0,columnBlockIndex=0;
		int keyIndex=0;
		try {
			message = Files.readAllBytes(Paths.get(inputFile));
			keys = Files.readAllBytes(Paths.get(keysFile));
			messageLength = message.length;
		} catch (IOException e) {
			e.printStackTrace();
		}
		cipher = new byte[messageLength];
		for(int i=0;i<messageLength/16;i++){
			byte[][] block = new byte[4][4];
			for(int j=16*i;j<(16*(i+1));j++){
				block[rowBlockIndex][columnBlockIndex] = message[j];
				columnBlockIndex++;
				if(columnBlockIndex==4){
					rowBlockIndex++;
					columnBlockIndex=0;
				}
			}
			for(int k=0;k<3;k++) {
				block = shiftRows(block);
				byte[][] key = getKey(keys,k);
				block = addRoundKey(block,key);
			}
			addToCipher(block,i*16);
		}
		test();
	}

	private static void test() {
		String rightCipher = "d:\\documents\\users\\dombo\\Downloads\\AES3_test_files\\cipher_short";
		try {
			byte [] result = Files.readAllBytes(Paths.get(rightCipher));
			for(int i=0;i<result.length;i++){
				if(result[i] != cipher[i])
					System.out.println("dglkfdlnglkdfsgkfmgl;kdfg");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void addToCipher(byte[][] block, int i) {
		for(int j=0;j<block.length;j++){
			for(int k=0;k<block[0].length;k++) {
				cipher[i] = block[j][k];
				i++;
			}
		}

	}

	private static byte[][] addRoundKey(byte[][] block, byte[][] key) {
		byte [][] res = new byte[4][4];
		for(int i=0;i<res.length;i++){
			for(int j=0;j<res.length;j++) {
				int one = (int)block[i][j];
				int two = (int)key[i][j];
				int xor = one ^ two;
				res[i][j] = (byte) (0xff & xor);
			}
		}
    	return res;
	}

	private static byte[][] getKey(byte[] keys, int k) {
    	byte[][] currentKey = new byte[4][4];
    	int rowIndex=0,columnIndex=0;
    	for(int i=16*k;i<(keys.length/3)*(k+1);i++){
    		currentKey[rowIndex][columnIndex] = keys[i];
    		columnIndex++;
    		if(columnIndex==4){
    			rowIndex++;
    			columnIndex=0;
			}
		}
		return currentKey;
	}

	private static byte[][] shiftRows(byte[][] block) {
		byte[][] newBlock = new byte[4][4];
		for (int i=0;i<newBlock.length;i++){
			newBlock[0][i]=block[0][i];
		}
		newBlock[1][0]=block[1][1];
		newBlock[1][1]=block[1][2];
		newBlock[1][2]=block[1][3];
		newBlock[1][3]=block[1][0];
		newBlock[2][0]=block[2][2];
		newBlock[2][1]=block[2][3];
		newBlock[2][2]=block[2][0];
		newBlock[2][3]=block[2][1];
		newBlock[3][0]=block[3][3];
		newBlock[3][1]=block[3][0];
		newBlock[3][2]=block[3][1];
		newBlock[3][3]=block[3][2];
    	return newBlock;
	}
}
