/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.text.TabStop;

/**
 *
 * @author ADMIN
 */
public class ValidateData {
    public boolean kiemTraEmail(String email) {
        String dinhDangEmail = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        boolean ktEmail = email.matches(dinhDangEmail);
        return ktEmail;
        // String dinhDangEmail nó giống như là format chuẩn cho email mình nhập vào, nếu sai cái
        // dịnh dạng này thì là lỗi.
        // Hàm matches dùng để xác định xem chuỗi email của mình có khớp với định dạng mình đã quy 
        // định trước hay không. hàm này trả về true , false nên mình khởi tạo 1 biến ktEmail rồi gán
        // cho nó. false thì báo lỗi.
    }
    
    public boolean kiemTraTen(String ten){
        // Đây là hàm kiểm tra xem tên có số hay không. Ko chứa thì trả về True, có chứa thì trả về false
        // ví dụ "Nguyễn Thị Thúy" trả về True, "Nguyễn 1 Thúy" trả về False
        for (int i = 0; i < 10; i++) {
            if(ten.contains(i+"")){
                return false;
            }
        }
        return true;
    }
    public String kiemTraSDT(String sdt){
        sdt = sdt.trim();
        sdt = sdt.replace(".", "");
        sdt = sdt.replace(",", "");
        sdt = sdt.replace(" ", "");
        sdt = sdt.replaceAll("\\s+", "");
        try{
            if(!sdt.startsWith(0+"")){
                return "Số điện thoại phải bắt đầu bằng số 0";
                
            } else if(sdt.length() != 10 && sdt.length() != 11){
                return "Số điện thoại phải có 10 hoặc 11 chữ số";
            }
            long sdt_long = Long.parseLong(sdt);
        } catch(NumberFormatException ex){
            return "Số điện thoại không được chứa chữ cái";
        } catch(Exception ex){
            return "Sai dinh dang" + ex.getMessage();
        }
        return "";
    }
    
    public String md5(String str) {
        String result = "";
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            BigInteger bigInteger = new BigInteger(1, digest.digest());
            result = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
}
    public String ChuanHoaChuoi (String s){
        // Đây là hàm chuẩn hóa chuối. Ví dụ: " nGUYỄN     THỊ thúy   " -> "Nguyễn Thị Thúy"
        // chuỗi "    " -> "" . Chuẩn hóa tên, email nhập vào, xong mới so xem nó bằng rỗng ko để đưa ra thông báo nhập vào ở thêm, sửa.
        s = s.trim();
        String chuanHoa = "";
        String a = "";
        String arr[] = s.split(" ");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim();
            if (!arr[i].isEmpty()) {
                a = Character.toString(arr[i].charAt(0)).toUpperCase().concat(arr[i].substring(1).toLowerCase());
                chuanHoa = chuanHoa.concat(a);
                chuanHoa = chuanHoa.concat(" ");
            }
        }
        chuanHoa = chuanHoa.trim();
        if(chuanHoa.equals(" ")) chuanHoa = "";
        System.out.println("Chuỗi sau khi chuẩn hóa: " + chuanHoa);
        return chuanHoa;
        
    }
    
//    public String DangTienTe (String number){
//        // Đây là hàm định dạng tiền tệ. Ví dụ: 123456789.25 -> 123,456,789.25
//        int x = number.length();
//        StringBuilder sb = new StringBuilder(number);
//        if(number.contains(".")){
//            while (x > 6) {
//                sb.insert(x - 6, ",");
//                x = x - 3;
//            }
//        
//        } else {
//            while (x > 3) {
//                sb.insert(x - 3, ",");
//                x = x - 3;
//            }
//        }
//        //System.out.println("Dạng tiền tệ: " + sb);
//        return sb.toString();
//    }
//    
    public String DangTienTe (String number){
        // Đây là hàm định dạng tiền tệ. Ví dụ: 
        // 123456 -> 123,456
        // 123456. -> 123,456.
        // 123456.1 -> 123,456.1
        // 123456789.25 -> 123,456,789.25
        number = number.trim();
        int x = number.length();
        StringBuilder sb = new StringBuilder(number);
        
        if (Pattern.matches("[0-9]+", number)) {
           while (x > 3) {
                sb.insert(x - 3, ",");
                x = x - 3;
            }
       } else if (Pattern.matches("[0-9]+(\\.)", number)) {
            while (x > 4) {
                sb.insert(x - 4, ",");
                x = x - 3;
            }
        } else if(Pattern.matches("[0-9]+(\\.[0-9])", number)){
            while (x > 5) {
                sb.insert(x - 5, ",");
                x = x - 3;
            }
        } else if (Pattern.matches("[0-9]+(\\.[0-9][0-9])", number)){
           while (x > 6) {
                sb.insert(x - 6, ",");
                x = x - 3;
            }
       }        //System.out.println("Dạng tiền tệ: " + sb);
        return sb.toString();
    }
    
    public String ReverseDangTienTe (String number) {
        return number.replace(",", "");
    }
    
    public boolean containsDigit(String s) {
        String regex = "(.)*(\\d)(.)*";      
        Pattern pattern = Pattern.compile(regex);
        String msg = s;
        boolean containsNumber = pattern.matcher(msg).matches();
        return containsNumber;
    }
    
    public boolean CheckNonNegInt(String x){
        // True nếu là số nguyên không âm
        x = x.trim();
        if (Pattern.matches("[0-9]+", x)) return true;
        else return false;
    }
    
    public int checkDiscount(String x){
        // return 0: đúng định dạng discount
        //return 1: đúng số thực nhưng <0 hoặc > 100
        // return 2: ko phải số thực
        x = x.trim();
        double y;
        if (Pattern.matches("[0-9]+(\\.[0-9]+)", x) || Pattern.matches("[0-9]+", x)) {
            y = Double.parseDouble(x);
            if(0<=y && y <=100) return 0;
            else return 1;
        }
        else return 2;
    }
    
    public boolean IntOrReal(String x){
        // hàm này trả về TRue nếu chuỗi truyền vào đúng dạng số thực or số nguyên (chỉ chứa số từ 0-9 và 1 dấu .) , Ví dụ: 123 ; 12.00
        // trả về false nếu chứa ký tự khác ngoài 0-9 và dấu chấm . (sai định dạng số nguyên or số thực) ví dụ: a123 ; 1.2.2, số âm cũng sai.
        x = x.replace(",", "");
        if (Pattern.matches("[0-9]+(\\.[0-9]+)", x) || Pattern.matches("[0-9]+", x)) {
               return true;
            } else return false;
    }
    
    // PBKDF2 - test thử: hàm này mạnh hơn md5 rất nhiều nhưng băm bị thay đổi
   
//    private static String generateStorngPasswordHash(String password) 
//        throws NoSuchAlgorithmException, InvalidKeySpecException
//    {
//        int iterations = 1000;
//        char[] chars = password.toCharArray();
//        byte[] salt = getSalt();
//
//        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
//        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//
//        byte[] hash = skf.generateSecret(spec).getEncoded();
//        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
//    }
//
//    private static byte[] getSalt() throws NoSuchAlgorithmException
//    {
//        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//        byte[] salt = new byte[16];
//        sr.nextBytes(salt);
//        return salt;
//    }
//
//    private static String toHex(byte[] array) throws NoSuchAlgorithmException
//    {
//        BigInteger bi = new BigInteger(1, array);
//        String hex = bi.toString(16);
//
//        int paddingLength = (array.length * 2) - hex.length();
//        if(paddingLength > 0)
//        {
//            return String.format("%0"  +paddingLength + "d", 0) + hex;
//        }else{
//            return hex;
//        }
//    }
//    
    
    
}
