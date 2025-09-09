from PySide6.QtWidgets import *
from PySide6.QtCore import *
from PySide6.QtGui import *

class Toolbar(QToolBar):
    def __init__(self, app):
        super().__init__()
        self.app = app

        # layout / appearance
        self.setOrientation(Qt.Vertical)
        self.setIconSize(QSize(50, 50))
        self.setMovable(False)
        self.setToolButtonStyle(Qt.ToolButtonTextUnderIcon)

        # sizes for collapse/expand
        self._expanded_width = 300
        self._collapsed_width = 3
        self._is_collapsed = False

        # allow animation to change the toolbar width — don't fix it
        self.setMinimumWidth(self._collapsed_width)
        self.setMaximumWidth(self._expanded_width)
        self.setSizePolicy(QSizePolicy.Preferred, QSizePolicy.Expanding)
        self.setStyleSheet("QToolBar { border: 1px solid #222; background: #151515; border-top-right-radius: 15px; border-bottom-right-radius: 15px; margin:0px }")

        #username
        userAction = QAction("Login", self)
        userAction.setToolTip("User Profile")
        userAction.setFont(QFont("Arial", 11, QFont.Bold))
        self.addAction(userAction)

        # search
        searchBar = QLineEdit(self)
        searchBar.setPlaceholderText("Search...")
        # add right-padding so the inline action doesn't overlap text
        searchBar.setStyleSheet("QLineEdit { background: #555; border: none; border-radius: 10px; padding: 5px 28px 5px 8px; color: white; margin: 5px; } QLineEdit:focus { background: #666; }")

        # create nicer search icons (normal + hover) and add as inline action
        normal_pix = QPixmap(16, 16)
        normal_pix.fill(Qt.transparent)
        p = QPainter(normal_pix)
        p.setRenderHint(QPainter.Antialiasing)
        pen = QPen(QColor("#cfcfcf"))
        pen.setWidthF(1.8)
        p.setPen(pen)
        p.drawEllipse(2, 2, 9, 9)
        p.drawLine(10, 10, 14, 14)
        p.end()

        hover_pix = QPixmap(16, 16)
        hover_pix.fill(Qt.transparent)
        p = QPainter(hover_pix)
        p.setRenderHint(QPainter.Antialiasing)
        pen = QPen(QColor("#ffffff"))
        pen.setWidthF(1.8)
        p.setPen(pen)
        p.drawEllipse(2, 2, 9, 9)
        p.drawLine(10, 10, 14, 14)
        p.end()

        normal_icon = QIcon(normal_pix)
        hover_icon = QIcon(hover_pix)

        # put the icon inside the QLineEdit on the right (TrailingPosition)
        inline_action = searchBar.addAction(normal_icon, QLineEdit.TrailingPosition)
        inline_action.triggered.connect(lambda: self.search(searchBar.text()))
        # customize the actual button that QLineEdit created for the action (cursor + hover icon)
        QTimer.singleShot(0, lambda: self._customize_search_action(inline_action, normal_icon, hover_icon))

        # add the line edit to the toolbar
        search_action = QWidgetAction(self)
        search_action.setDefaultWidget(searchBar)
        self.addAction(search_action)

        # activate search:
        # - press Enter to run search once
        searchBar.returnPressed.connect(lambda: self.search(searchBar.text()))
        # - optionally enable live search while typing
        # searchBar.textChanged.connect(lambda t: self.search(t))

        # collapse action (optional menu action)

        # floating edge button (created after the toolbar is added to a window)
        self._edge_btn = None
        QTimer.singleShot(0, self._init_edge_button)

        # animation for collapse / expand (animate maximumWidth so layouts respect it)
        self._anim = QPropertyAnimation(self, b"maximumWidth", self)
        self._anim.setDuration(500)
        self._anim.setEasingCurve(QEasingCurve.OutCubic)
        self._anim.valueChanged.connect(lambda v: QTimer.singleShot(0, self.reposition_edge_button))
        # ensure geometry updates and button repositions after animation completes
        self._anim.finished.connect(self._on_anim_finished)
        self._anim_target_collapsed = None

    def _init_edge_button(self):
        win = self.window()
        if not win:
            return
        self._edge_btn = QPushButton(win)
        # use a clean chevron icon (left when expanded, right when collapsed)
        self._edge_btn.setIcon(self._edge_icon())
        self._edge_btn.setIconSize(QSize(18, 18))
        self._edge_btn.setCursor(Qt.PointingHandCursor)  # change cursor on hover
        self._edge_btn.setFlat(True)
        self._edge_btn.setFixedSize(36, 36)
        self._edge_btn.setToolTip("Toggle toolbar")
        self._edge_btn.setStyleSheet("""
            QPushButton {
                background: #2b2b2b;
                color: white;
                border: 2px solid #1f1f1f;
                border-radius: 18px;
            }
            QPushButton:hover {
                background: #3a3a3a;
            }
        """)
        self._edge_btn.clicked.connect(self.collapse_toolbar)  # toggle collapse on click
        # keep the edge button visible always (so user can expand after collapse)
        self._edge_btn.setVisible(True)
        self._edge_btn.raise_()

        # reposition on window resize/move
        win.installEventFilter(self)
        QTimer.singleShot(0, self.reposition_edge_button)

    def _edge_icon(self, for_target_collapsed: bool | None = None) -> QIcon:
        """
        Return an icon for the edge button.
        - when toolbar is expanded -> show left chevron (collapse)
        - when toolbar is collapsed -> show right chevron (expand)
        if for_target_collapsed is provided, return icon for that target state.
        """
        target_collapsed = self._is_collapsed if for_target_collapsed is None else bool(for_target_collapsed)
        # chevrons from style (fallback to simple pixmap if not available)
        if target_collapsed:
            return self._make_chevron_icon("right")
        else:
            return self._make_chevron_icon("left")

    def reposition_edge_button(self):
        if not self._edge_btn:
            return
        win = self.window()
        if not win:
            return
        tb_top_left = self.mapTo(win, QPoint(0, 0))
        # use the toolbar's current width so the button follows the animated resize smoothly
        edge_x = tb_top_left.x() + self.width()
        btn_h = self._edge_btn.height()
        edge_y = tb_top_left.y() + (self.height() - btn_h) // 2
        # place button so its center sits on the toolbar right edge
        btn_left = edge_x - (self._edge_btn.width() // 2)
        self._edge_btn.move(btn_left, edge_y)
        self._edge_btn.raise_()

    def eventFilter(self, obj, event):
        # reposition edge button on main-window resize/move
        if obj is self.window() and getattr(self, "_edge_btn", None):
            if event.type() in (QEvent.Resize, QEvent.Move):
                QTimer.singleShot(0, self.reposition_edge_button)
        # handle hover enter/leave on the inline search button to change icon & cursor
        if getattr(self, "_search_btn", None) is obj:
            if event.type() == QEvent.Enter:
                obj.setIcon(self._search_hover_icon)
                obj.setCursor(Qt.PointingHandCursor)
            elif event.type() == QEvent.Leave:
                obj.setIcon(self._search_normal_icon)
        return super().eventFilter(obj, event)

    def showEvent(self, event):
        super().showEvent(event)
        if self._edge_btn:
            self._edge_btn.setVisible(True)
            QTimer.singleShot(0, self.reposition_edge_button)

    # do not hide the edge button when toolbar is hidden/collapsed — keep it available
    # def hideEvent(self, event): ...

    def collapse_toolbar(self):
        # toggle collapsed state with a smooth width animation
        if self._anim.state() == QAbstractAnimation.Running:
            return  # ignore while animating

        start = max(1, self.width())
        if not self._is_collapsed:
            end = self._collapsed_width
            self._anim_target_collapsed = True
        else:
            end = self._expanded_width
            self._anim_target_collapsed = False

        # update edge button icon to show the target action immediately
        if self._edge_btn:
            self._edge_btn.setIcon(self._edge_icon(self._anim_target_collapsed))

        # animate maximumWidth so layout respects the change
        self._anim.stop()
        self._anim.setStartValue(start)
        self._anim.setEndValue(end)
        # ensure the widget can shrink/grow by adjusting maximumWidth during animation
        self.setMaximumWidth(start)
        self._anim.start()

    def _on_anim_finished(self):
        # finalize state after animation so the edge button follows smoothly during animation
        if self._anim_target_collapsed is not None:
            self._is_collapsed = bool(self._anim_target_collapsed)
            self._anim_target_collapsed = None
        self.updateGeometry()
        QTimer.singleShot(0, self.reposition_edge_button)

    def search(self, text):
        print("Searching for: "+text)

    def _customize_search_action(self, action: QAction, normal_icon: QIcon, hover_icon: QIcon):
        """Find the QToolButton created by QLineEdit.addAction and set cursor + hover icon swap."""
        self._search_btn = None
        self._search_normal_icon = normal_icon
        self._search_hover_icon = hover_icon
        # action.parent() is the QLineEdit
        parent = action.parent()
        if not isinstance(parent, QLineEdit):
            parent = None
        if parent is None:
            return
        for btn in parent.findChildren(QToolButton):
            # defaultAction() returns the QAction that the button represents
            try:
                if btn.defaultAction() is action:
                    btn.setCursor(Qt.PointingHandCursor)
                    btn.setIcon(normal_icon)
                    btn.installEventFilter(self)
                    self._search_btn = btn
                    break
            except Exception:
                continue
        
    def _make_chevron_icon(self, direction: str, color: QColor = QColor("white")) -> QIcon:
        size = 18
        pm = QPixmap(size, size)
        pm.fill(Qt.transparent)
        p = QPainter(pm)
        p.setRenderHint(QPainter.Antialiasing)
        pen = QPen(color)
        pen.setWidth(2)
        p.setPen(pen)

        if direction == "left":
            points = [
                QPointF(size*0.65, size*0.25),
                QPointF(size*0.35, size*0.5),
                QPointF(size*0.65, size*0.75)
            ]
        elif direction == "right":
            points = [
                QPointF(size*0.35, size*0.25),
                QPointF(size*0.65, size*0.5),
                QPointF(size*0.35, size*0.75)
            ]
        else:
            points = []

        if points:
            p.drawPolyline(QPolygonF(points))
        
        p.end()
        return QIcon(pm)
