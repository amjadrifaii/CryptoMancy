from PySide6.QtWidgets import *
from PySide6.QtCore import *
from . import toolbar, top_menu, main_view_box, chat_box_view
class MainWindow(QMainWindow):
    def __init__(self,app):
        super().__init__()
        self.setWindowTitle("Delta Project")
        self.resize(2000, 3000)
        self.app = app
        self.toolbar = toolbar.Toolbar(app)
        self.topMenu = top_menu.TopMenu(app)
        self.mainViewBox = main_view_box.MainViewBox(app)
        self.bottomViewBox = chat_box_view.ChatBoxView(app)

        self.fullLayout = QHBoxLayout()
        self.mainLayout = QVBoxLayout()

        self.mainLayout.addWidget(self.mainViewBox)
        self.mainLayout.addWidget(self.bottomViewBox)
        
        self.fullLayout.addWidget(self.toolbar)
        self.fullLayout.addLayout(self.mainLayout)

        centralWidget = QWidget()
        centralWidget.setLayout(self.fullLayout)
        self.setCentralWidget(centralWidget)

        self.setMenuBar(self.topMenu)

        self.setStyleSheet("background-color: #222; margin:0px; padding:0px; border:none;")
        self.fullLayout.setContentsMargins(0, 0, 0, 0)
        self.fullLayout.setSpacing(0)
        self.mainLayout.setContentsMargins(0, 0, 0, 0)
        self.mainLayout.setSpacing(0)