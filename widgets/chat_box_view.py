from PySide6.QtWidgets import *
from PySide6.QtCore import *
from PySide6.QtGui import *


class ChatBoxView(QFrame):
    def __init__(self, app):
        super().__init__()
        self.app = app

        # Frame styling
        self.setFrameShape(QFrame.StyledPanel)
        self.setFrameShadow(QFrame.Raised)
        self.setStyleSheet("""
            QFrame {
                background-color: #222;
                border-radius: 17px;
                margin: 0px;
                padding: 5px;
            }
        """)

        # Layout
        self.layout = QVBoxLayout()
        self.layout.setContentsMargins(5, 5, 5, 5)  # small padding inside
        self.setLayout(self.layout)

        # Prompt Box
        self.promptBox = QTextEdit()
        self.promptBox.setAcceptRichText(False)  # plain text only
        self.promptBox.setMinimumHeight(40)        # good for single-line feel
        self.promptBox.setStyleSheet("""
            QTextEdit {
                font-size: 16px;
                border-radius: 15px;
                padding: 8px;
                color: white;
                background: #111;
                font-family: 'Arial';
            }
            QTextEdit:focus {
                background: #151515;
            }
        """)
        
        self.promptBox.setPlaceholderText("Enter your prompt here...")
        self.layout.setAlignment(Qt.AlignCenter)
        # Add prompt box to layout
        self.layout.addWidget(self.promptBox)
