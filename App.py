import tkinter
from tkinter import filedialog
import PyQt6.QtWidgets as widgets
from PyQt6.QtCore import QSize, Qt

tkinter.Tk().withdraw()

folder_path = filedialog.askdirectory()

print(folder_path)

class MainWindow(widgets.QMainWindow):
    def __init__(self):
        super().__init__()

        self.setWindowTitle("3310 Path Planner")

        self.button_is_checked = True

        self.button = widgets.QPushButton("Press Me!")
        self.button.setCheckable(True)
        self.button.clicked.connect(self.the_button_was_clicked)
        self.button.clicked.connect(self.the_button_was_toggled)
        self.button.setChecked(self.button_is_checked)

        self.input = widgets.QLineEdit()
        self.input.textChanged.connect(self.button.setText)

        layout = widgets.QVBoxLayout()
        layout.addWidget(self.input)
        layout.addWidget(self.button)
        
        container = widgets.QWidget()
        container.setLayout(layout)

        # Set the central widget of the Window.
        self.setCentralWidget(container)
        self.resize(QSize(400,300))

    def the_button_was_clicked(self):
        print("Clicked!")

    def the_button_was_toggled(self, checked):
        self.button_is_checked = checked

        print(self.button_is_checked)

# You need one (and only one) QApplication instance per application.
# Pass in sys.argv to allow command line arguments for your app.
# If you know you won't use command line arguments QApplication([]) works too.
app = widgets.QApplication([])

# Create a Qt widget, which will be our window.
window = MainWindow()
window.show()  # IMPORTANT!!!!! Windows are hidden by default.

# Start the event loop.
app.exec()