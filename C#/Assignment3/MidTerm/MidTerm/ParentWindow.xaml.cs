using System;
using System.Windows;

namespace MidTerm
{
    /// <summary>
    /// Interaction logic for ParentWindow.xaml
    /// </summary>
    public partial class ParentWindow : Window
    {
        public ParentWindow()
        {
            InitializeComponent();
        }

        private void ChildFromParent_Click(object sender, RoutedEventArgs e)
        {
            if (MainWindow.m_cCounter == 0)
            {
                ChildWindow child = new ChildWindow();
                child.Show();
                MainWindow.m_cCounter++;
            }
            else 
            {
                MessageBox.Show("Child Window already exists", "Child Window Exists");
            }
        }

        private void ParentWindow_Closed(object sender, EventArgs e)
        {
            MainWindow.m_pCounter = 0;
        }
    }
}
