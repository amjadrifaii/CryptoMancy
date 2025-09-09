from PySide6.QtWidgets import *
from PySide6.QtCore import *
from PySide6.QtGui import QAction, QIcon, QPixmap, QPainter, QColor  # changed import

class TopMenu(QMenuBar):
    def __init__(self, app):
        super().__init__()
        self.app = app

        self.setLayoutDirection(Qt.RightToLeft)

        self.setStyleSheet("QMenuBar { background: #111111; color: white; margin:0px } QMenuBar::item { background: #151515; } QMenuBar::item:selected { background: #222; } QMenu { background: #151515; color: white; } QMenu::item:selected { background: #222; }")

        spacer = QWidget(self)
        spacer.setSizePolicy(QSizePolicy.Expanding, QSizePolicy.Preferred)
        spacer.setAttribute(Qt.WA_TransparentForMouseEvents)  # don't steal events / help layout
        spacer_action = QWidgetAction(self)
        spacer_action.setDefaultWidget(spacer)
        self.addAction(spacer_action)

        # create a tinted close icon and use it in the QAction
        orig_pix = self.style().standardPixmap(QStyle.SP_TitleBarCloseButton, None, self)

        def tint_pixmap(pixmap: QPixmap, hex_color: str) -> QPixmap:
            if pixmap.isNull():
                return pixmap
            tinted = QPixmap(pixmap.size())
            tinted.fill(Qt.transparent)
            painter = QPainter(tinted)
            painter.drawPixmap(0, 0, pixmap)
            painter.setCompositionMode(QPainter.CompositionMode_SourceIn)
            painter.fillRect(tinted.rect(), QColor(hex_color))
            painter.end()
            return tinted

        # change this hex color for the X color
        tinted_pix = tint_pixmap(orig_pix, "#ffffff")
        close_icon = QIcon(tinted_pix)
        quit_action = QAction(close_icon, "", self)
        quit_action.setToolTip("Quit")
        quit_action.triggered.connect(self.quit_app)
        self.addAction(quit_action)


    def quit_app(self):
        QCoreApplication.quit()


