import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JanelaOP extends JFrame {

    JLabel nomeDoArquivoL = new JLabel("Nome do arquivo");

    JLabel titulo1 = new JLabel("Path de origem do arquivo");
    JLabel titulo2 = new JLabel("Path de destino do arquivo");
    JLabel titulo3 = new JLabel("Tipo do arquivo a ser filtrado");

    JTextField arquivoFonte = new JTextField();
    JTextField caminhodoArquivo = new JTextField();
    JTextField nomeDoArquivo = new JTextField();
    JTextField extensaoDoArquivo = new JTextField();


    JButton gerar = new JButton("Criar");
    
    JanelaOP(){

        setLayout(null);
        titulo1.setBounds(15, 15, 300, 50);//path de origem
        arquivoFonte.setBounds(15, 50, 250, 30);//caminho da origem incluindo nome do arquivo
        
        titulo2.setBounds(15, 5, 250, 200);//path de destino
        caminhodoArquivo.setBounds(15, 120, 250, 30);//caminho de destino

        nomeDoArquivoL.setBounds(15, 160, 250, 30);//nome arquivo label
        nomeDoArquivo.setBounds(15, 190, 250, 30);//nome do arquivo que vai ser criado
        
        
        titulo3.setBounds(15, 230, 200, 30);//label da extensão a ser filtrada
        extensaoDoArquivo.setBounds(15, 260, 200, 30);//tipo da extensão a ser filtrada(incluir o . )
        

        gerar.setBounds(40, 350, 200, 30);
        gerar.addActionListener(this::onkGerar);
        
        add(titulo1);
        add(titulo2);
        add(titulo3);
        add(caminhodoArquivo);
        add(arquivoFonte);
        add(nomeDoArquivoL);
        add(nomeDoArquivo);
        add(extensaoDoArquivo);
        add(gerar);
        setSize(300, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void onkGerar(ActionEvent e){

        String str1=arquivoFonte.getText();
        String str2=caminhodoArquivo.getText();
        String str3=nomeDoArquivo.getText();
        String str4=extensaoDoArquivo.getText();

        processo(str1,str2,str3,str4);

    }


    public static void processo(String arquivoFonte, String caminhoDoArquivo,String nomedoarquivo,String _extensaoDoArquivo) {

        if(_extensaoDoArquivo.equals("")){//essa declaração nao pode ser null
            _extensaoDoArquivo=".dll";
        }
  
        String path = arquivoFonte;
        String nomeDoArquivo=nomedoarquivo;
        String destino=caminhoDoArquivo;
        String extensaoDoArquivo = _extensaoDoArquivo;

        List<String> Dlls = new ArrayList<>();

        String outPath = destino + "\\"+nomeDoArquivo + ".bat";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();

            //primeiro loop apenas para iniciar a primeira leitura
            String[] lineSplit = line.split(",");
            for (int i = 0; i < lineSplit.length; i++) {
                if (lineSplit[i].contains(extensaoDoArquivo)) {
                    Dlls.add(lineSplit[i]);
                }
            }

            while (line != null) { //loop principal até o documento ficar vazio
                lineSplit = line.split(",");
                for (int i = 0; i < lineSplit.length; i++) {
                    if (lineSplit[i].contains(extensaoDoArquivo)) {
                        Dlls.add(lineSplit[i]);
                    }
                }
                line = br.readLine();
            }

            for (String string : Dlls) {
                System.out.println(string);
            }

        } catch (IOException exception) {
            System.out.println("Erro: " + exception.getMessage());
        }

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(outPath))) {//escreve os arquivos e suas extensões em um .bat
            
            for (String dll : Dlls) {
                wr.write("regsvr32 /c /s " + dll);
                wr.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }


}


