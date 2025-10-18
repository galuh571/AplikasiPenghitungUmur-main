import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class AplikasiPenghitungUmur extends JFrame {

    private JPanel panel;
    private JLabel lblJudul, lblTanggalLahir, lblUmur, lblNextBirthday;
    private JDateChooser dateChooser;
    private JButton btnHitung;
    private JTextField txtUmur, txtNextBirthday;
    private JTextArea txtInfo;
    private JScrollPane scrollPane;

    public AplikasiPenghitungUmur() {
        setTitle("Aplikasi Penghitung Umur");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(200, 230, 250));

        lblJudul = new JLabel("Aplikasi Penghitung Umur");
        lblJudul.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblJudul.setBounds(100, 20, 300, 30);
        panel.add(lblJudul);

        lblTanggalLahir = new JLabel("Tanggal Lahir:");
        lblTanggalLahir.setBounds(40, 80, 100, 25);
        panel.add(lblTanggalLahir);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(150, 80, 200, 25);
        panel.add(dateChooser);

        btnHitung = new JButton("Hitung");
        btnHitung.setBounds(150, 120, 100, 25);
        panel.add(btnHitung);

        lblUmur = new JLabel("Umur Anda:");
        lblUmur.setBounds(40, 160, 100, 25);
        panel.add(lblUmur);

        txtUmur = new JTextField();
        txtUmur.setBounds(150, 160, 200, 25);
        txtUmur.setEditable(false);
        panel.add(txtUmur);

        lblNextBirthday = new JLabel("Ulang Tahun Berikutnya:");
        lblNextBirthday.setBounds(40, 200, 200, 25);
        panel.add(lblNextBirthday);

        txtNextBirthday = new JTextField();
        txtNextBirthday.setBounds(220, 200, 130, 25);
        txtNextBirthday.setEditable(false);
        panel.add(txtNextBirthday);

        txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        scrollPane = new JScrollPane(txtInfo);
        scrollPane.setBounds(40, 240, 350, 100);
        panel.add(scrollPane);

        add(panel);

        btnHitung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungUmur();
            }
        });

        dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    txtInfo.setText("");
                }
            }
        });
    }

    private void hitungUmur() {
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Pilih tanggal lahir terlebih dahulu!");
            return;
        }

        LocalDate tanggalLahir = dateChooser.getDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate sekarang = LocalDate.now();

        if (tanggalLahir.isAfter(sekarang)) {
            JOptionPane.showMessageDialog(this, "Tanggal lahir tidak boleh di masa depan!");
            return;
        }

        Period umur = Period.between(tanggalLahir, sekarang);
        txtUmur.setText(umur.getYears() + " tahun, " + umur.getMonths() + " bulan, " + umur.getDays() + " hari");

        LocalDate ulangTahunBerikutnya = tanggalLahir.withYear(sekarang.getYear());
        if (!ulangTahunBerikutnya.isAfter(sekarang)) {
            ulangTahunBerikutnya = ulangTahunBerikutnya.plusYears(1);
        }

        long hariMenujuUlangTahun = ChronoUnit.DAYS.between(sekarang, ulangTahunBerikutnya);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID"));
        txtNextBirthday.setText(ulangTahunBerikutnya.format(formatter));

        txtInfo.append("Umur dihitung dari tanggal lahir: " + tanggalLahir.format(formatter) + "\n");
        txtInfo.append("Hari ulang tahun berikutnya: " + ulangTahunBerikutnya.format(formatter) + "\n");
        txtInfo.append("Jumlah hari menuju ulang tahun berikutnya: " + hariMenujuUlangTahun + " hari\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AplikasiPenghitungUmur().setVisible(true);
        });
    }
}
