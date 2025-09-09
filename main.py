from PySide6.QtWidgets import *
from widgets import main_window
import sys

app = QApplication(sys.argv)
window = main_window.MainWindow(app)
window.showFullScreen()
app.exec()