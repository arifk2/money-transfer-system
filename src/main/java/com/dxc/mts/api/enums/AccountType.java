package com.dxc.mts.api.enums;

public enum AccountType {
	SAVINGS {
		@Override
		public String toString() {
			return "SAVINGS";
		}
	},

	CURRENT {
		@Override
		public String toString() {
			return "CURRENT";
		}
	};

}
