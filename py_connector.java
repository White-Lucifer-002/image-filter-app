import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class py_connector{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        
        int choice = 0;
        HttpURLConnection conn = null;
        DataOutputStream os = null;
        byte[] data = new byte[20];
        String output;
        File file = new File("test-image.jpg");
        
        try{
            data = Files.readAllBytes(file.toPath());
        }
        catch (IOException e){
            System.out.println("");
        }
        
        String base64str = Base64.getEncoder().encodeToString(data);

        while(choice != -1){
            System.out.println("Enter choice:\n0.Upload\n1.Blur\n2.Exit");
            choice = sc.nextInt();
            if (choice == 0){
                try{
                    URL url = new URL("http://127.0.0.1:5000/upload/"); //important to add the trailing slash after add
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "images/jpeg");
                    conn.setRequestProperty("Content-Length", Integer.toString(base64str.length()));
                    os = new DataOutputStream(conn.getOutputStream());
                    System.out.println(data);
                    os.write(data);
                    os.flush();

                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                   
                    output = br.readLine();
                    System.out.println("Output from Server .... \n"+output);
                    conn.disconnect();

                }catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }finally{
                    if(conn != null)
                    {
                        conn.disconnect();
                    }
                }
            }
            else if(choice == 1){
                try{
                    int val = 41;
                    URL url = new URL("http://127.0.0.1:5000/blur/"+val);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Content-Length", Integer.toString(base64str.length()));
                    os = new DataOutputStream(conn.getOutputStream());
                    os.write(data);
                    os.flush();

                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                    System.out.println("Output from Server Blur .... \n");
                    output = br.readLine();

                    byte[] str = Base64.getDecoder().decode(output.getBytes());
                    System.out.println(str);

                    FileOutputStream fos = new FileOutputStream("blur-image.jpg"); //change path of image according to you
                    fos.write(str);
                    fos.close();

                    // BufferedImage imag = ImageIO.read(new ByteArrayInputStream(img));
		            // ImageIO.write(imag, "jpg", new File("blur-img.jpg"));
                    System.out.println("image created");

                    conn.disconnect();
                        
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }finally{
                    if(conn != null)
                    {
                        conn.disconnect();
                    }
                }
            }
        }
        sc.close();
    }
}
