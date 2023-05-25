import java.util.ArrayList;

public class Decoding {
    String encodedString;
    int senderLengthField;
    int receiverLengthField;
    Decoding(String encodedString){
        this.encodedString = encodedString;
    }
    public long numberToInt(String number){
        ArrayList<String> binaryList = new ArrayList<>();
        ArrayList<String> hexList = new ArrayList<>();

        for (int i = 0; i < number.length()-1; i +=2) {
            //85
            String hex = number.charAt(i+1) +"";//8

            int decimal = Integer.parseInt(hex, 16);
            String binary = String.format("%04d", Integer.parseInt(Integer.toBinaryString(decimal)));
            binaryList.add(binary);

            hex = number.charAt(i) +"";//8
            decimal = Integer.parseInt(hex, 16);
            binary = String.format("%04d", Integer.parseInt(Integer.toBinaryString(decimal)));

            binaryList.add(binary);
        }


        String binaryNumber = String.join("", binaryList);

        if (binaryNumber.endsWith("1111")) {
            binaryNumber = binaryNumber.substring(0, binaryNumber.length() - 4);
        }

        ArrayList<Integer> finalIntegerList = new ArrayList<>();
        for(int i=0; i<binaryNumber.length();i+=4){
            finalIntegerList.add(Integer.parseInt(binaryNumber.substring(i,i+4),2));
        }
        long finalNumber = 0;
        for(int i =0; i<finalIntegerList.size();i++){
            finalNumber *= 10;
            finalNumber+=finalIntegerList.get(i);
        }

        return finalNumber;

    }
    public String senderNumber(){
        String senderLengthNumber = encodedString.substring(0,2);
        String typeOfSenderNumber = encodedString.substring(2,4);
        int senderLength = Integer.parseInt(senderLengthNumber,16);
        //System.out.println(senderLength);
        senderLengthField = senderLength*2+2;//flag for future usages//16
        //System.out.println(senderLengthField);
        return encodedString.substring(4, (senderLength*2+2));
    }
    public String receiverNumber(){
        String receiverLengthNumber = encodedString.substring(senderLengthField+2,senderLengthField+4);
        String typeOfReceiverNumber = encodedString.substring(senderLengthField+4,senderLengthField+6);
        int receiverLength = Integer.parseInt(receiverLengthNumber,16);
        //System.out.println(receiverLength);
        int count=0;
        String excludeF = encodedString.substring(senderLengthField+6, senderLengthField + 6 + receiverLength);
        for(int i=0;i<excludeF.length();i++){// size of it did not count F, so we need to exclude it
            if (excludeF.charAt(i) == 'F') {
                count = -1;
                break;
            }
        }
        receiverLengthField = (senderLengthField + 6 + receiverLength +count);
        //System.out.println(receiverLengthField);
        String receiverNumber = encodedString.substring(senderLengthField+6,senderLengthField + 6 + receiverLength +count);
        return receiverNumber;
    }
    public String message(){
        String messageLenght = encodedString.substring(receiverLengthField+4,receiverLengthField+6);
        //System.out.println(messageLenght+ "sdfsdf");
        int statingInd = receiverLengthField+6;
        String message = encodedString.substring(statingInd );
        return sevenBitDecoder(message);
    }
    public static String sevenBitDecoder(String message) {
        int count = 0;
        String result = "";
        int bits = 0;
        for (int i = 0; i < message.length(); i += 2) {
            String hex = message.substring(i, i + 2);
            int value = Integer.parseInt(hex, 16);
            bits |= value << count;
            count += 8;
            while (count >= 7) {
                int octet = bits & 0b01111111;
                result += (char) octet;
                bits >>= 7;
                count -= 7;
            }
        }
        return result;
    }
}

