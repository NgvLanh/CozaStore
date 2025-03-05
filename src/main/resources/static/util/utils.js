const formatVnCurrency = (amount) => {
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND',
  }).format(amount);
};

const parseVnCurrency = (currencyStr) => {
  return Number(currencyStr.replace(/[^\d.-]/g, ''));
};
