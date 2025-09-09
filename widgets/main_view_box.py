from PySide6.QtWidgets import *
from PySide6.QtCore import *
from PySide6.QtGui import *
import requests

class MainViewBox(QFrame):
    # class BinanceAPI:
    #     def __init__(self, base_url="https://api.binance.com"):
    #         self.base_url = base_url

    #     def get_symbol_price(self, symbol: str):
    #         """Get latest price for a specific symbol"""
    #         endpoint = f"/api/v3/ticker/price"
    #         params = {"symbol": symbol.upper()}
    #         response = requests.get(self.base_url + endpoint, params=params)
    #         if response.status_code == 200:
    #             return response.json()
    #         else:
    #             raise Exception(f"Error {response.status_code}: {response.text}")

    # api = BinanceAPI()

    def __init__(self, app):
        super().__init__()
        self.app = app
        self.setStyleSheet("background-color: #222; border: none; margin:0px")
        self.setFrameShape(QFrame.StyledPanel)
        self.setFrameShadow(QFrame.Raised)

        self.layout = QVBoxLayout()
        self.setMinimumHeight(960)

        self.textBox = QLabel("Empty Data")
        # price_data = self.api.get_symbol_price("ETHUSDT")
        # self.textBox.setText(f"Symbol: {price_data['symbol']}, Price: {price_data['price']}")
        # self.textBox.setStyleSheet("color: white; font-size: 20px;")

        self.layout.addWidget(self.textBox)
        self.setLayout(self.layout)
