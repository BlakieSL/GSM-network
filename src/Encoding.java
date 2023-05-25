import java.util.ArrayList;

public class Encoding {
    public  String semiOctet(String number){
        char [] digits = number.toCharArray();
        boolean isEven= digits.length%2==0;
        ArrayList<String> binaryList = new ArrayList<>();
        ArrayList<String> hexList = new ArrayList<>();
        for(int i=0; i<digits.length;i++){
            int digit = Character.getNumericValue(digits[i]);
            String binary = String.format("%04d", Integer.parseInt(Integer.toBinaryString(digit)));
            binaryList.add(binary);
        }
        if(!isEven){
            binaryList.add("1111");
        }
        for(int i=0; i<binaryList.size();i+=2){
            String binary = binaryList.get(i+1) + binaryList.get(i);
            int decimal = Integer.parseInt(binary, 2);
            String hexadecimal = String.format("%02X", decimal);
            hexList.add(hexadecimal);
        }

        return String.join("",hexList);
    }
    public  String buildSenderNumber(String number){
        String addressLength = addressLength(number);
        String type = TypeofaddressoftheSMSC();
        String value = addressValue(number);
        return addressLength + type + value;
    }
    public  String addressLength(String number)   {
        /*
        integer representation of the number of octets within the type
        of address and address value fields
         */
        String type = TypeofaddressoftheSMSC();
        String value = addressValue(number);
        int length = (type.length() + value.length())/2;

        return String.format("%02X", length).toUpperCase();
    }
    public  String TypeofaddressoftheSMSC(){
        /*
        most cases hexadecimal 91(binary 1 001 0001)
         bits: 1 _ _ _ | _ _ _ _
          where bits 6 5 4 are type of number
           0 0 0 -unknown
           0 0 1 - international(most cases it is the case)
           0 1 0 - national number
           1 1 1 - reserved for extension
          where bits 3 2 1 0 are numbering plan-indentification
             0 0 0 0 - unknown
             0 0 0 1 - ISDN(most cases it is the case)
             1 1 1 1 - reserved for extension
         */
        return "91";
    }
    public  String addressValue(String number){
        return semiOctet(number);
    }

    public  String firstOctet(){
        return "01";
    }
    /*
    public static String TPMR(){
        return "";
    }
//  it is not used in example!!
     */
    public  String TPDA(String number){//addressLength(some chages) + type + addressvalue
        String result;
        String type = "91";
        String addressValue = semiOctet(number);
        int addressLength=0;
        for(int i=0; i<addressValue.length();i++){
            char chr = addressValue.charAt(i);
            if( chr != 'F'){
                addressLength++;
            }
        }
        String hexLength = String.format("%02X", addressLength).toUpperCase();
        result = hexLength + type + addressValue;

        return result;
    }
    public  String TPPID(){
        return "00";
    }
    public  String TPDCS(){
        return "00";
    }
    public  String TPUDL(String message){//length of encoded message
        int length = sevenBit(message).length()/2;

        return String.format("%02X", length).toUpperCase();
    }

    public  String sevenBit(String message) {
        int count = 0;
        String result = "";
        int bits = 0;

        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            int value = c & 0b01111111; // 0111 1111

            bits |= value << count;
            count += 7;

            while (count >= 8) {//form an octet, then remove bits, then were used to create octet
                int octet = bits & 0b11111111; // 1111 1111
                result += String.format("%02x", octet).toUpperCase();
                bits >>= 8;
                count -= 8;
            }
        }
        if (count > 0) {//form an octet if there are not enough bits by adding 0xFF(1's)
            int octet = bits & 0b11111111;//1111 1111
            result+=String.format("%02x", octet).toUpperCase();
        }
        return result;
    }
    public  String encoded(String message, String sender, String receiver){
        return buildSenderNumber(sender) + firstOctet() + TPDA(receiver) + TPPID() + TPDCS() + TPUDL(message) + sevenBit(message);
    }
}

