using System.Windows;
using System.Windows.Controls;
using System.Windows.Media;

namespace MidTerm
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public static int m_pCounter = 0;
        public static int m_cCounter = 0;
        public MainWindow()
        {
            InitializeComponent();

            SliderExpander.Expanded += SliderExpander_Expanded;
            ImageExpander.Expanded += ImageExpander_Expanded;
        }

        private void ImageExpander_Expanded(object sender, RoutedEventArgs e)
        {
            SliderExpander.IsExpanded = false;
        }

        private void SliderExpander_Expanded(object sender, RoutedEventArgs e)
        {
            ImageExpander.IsExpanded = false;
        }

        private void CheckBox_Click(object sender, RoutedEventArgs e)
        {
            if (CheckBox.IsChecked == true)
            {
                CheckLabel.Content = "CheckBox is Checked";
            }
            else
            {
                CheckLabel.Content = "CheckBox is not Checked";
            }
        }

        private void ButtonTab1_Click(object sender, RoutedEventArgs e)
        {
            bigTab.SelectedIndex = 1;
        }

        private void Slider_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            SliderLabel.Content = string.Format("Slider Value: {0:N2}", e.NewValue);
        }

        private void SpawnParent_Click(object sender, RoutedEventArgs e)
        {
            if (m_pCounter == 0)
            {
                ParentWindow parent = new ParentWindow();
                parent.Show();
                m_pCounter++;
            }
            else
            {
                MessageBox.Show("Parent window already exists!", "Parent Window Exists");
            }

        }

        private void SpawnChild_Click(object sender, RoutedEventArgs e)
        {

            if (m_cCounter == 0 && m_pCounter != 0)
            {
                ChildWindow child = new ChildWindow();
                child.Show();
                m_cCounter++;
            }
            else if (m_cCounter != 0)
            {
                MessageBox.Show("Child window already exists!", "Child Window Exists");
            }
            else
            {
                MessageBox.Show("Parent window doesn't exists!", "Parent Doesn't Exists");
            }
        }

        private void ThumbsUp_MouseEnter(object sender, System.Windows.Input.MouseEventArgs e)
        {
            ThumbsUp.Stretch = Stretch.UniformToFill;
            ThumbsUp.Width = ImageExpander.Width;
        }

        private void ThumbsUp_MouseLeave(object sender, System.Windows.Input.MouseEventArgs e)
        {
            ThumbsUp.Stretch = Stretch.Uniform;
            ThumbsUp.Height = ImageExpander.Height;
        }

        private void TextBox_KeyDown(object sender, System.Windows.Input.KeyEventArgs e)
        {
            if (e.Key.ToString().ToLower() == "q" || e.Key.ToString().ToLower() == "y" || e.Key.ToString().ToLower() == "z")
            {
                e.Handled = true;
            }
        }

        private void TextBox_KeyUp(object sender, System.Windows.Input.KeyEventArgs e)
        {
            TextLabel.Content = TextBox.Text;
        }
    }
}
