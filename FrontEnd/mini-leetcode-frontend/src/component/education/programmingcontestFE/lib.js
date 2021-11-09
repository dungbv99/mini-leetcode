export default function lib() {
  return ;
}

export function sleep(ms){
  return new Promise(resolve => setTimeout(resolve, ms));
}

export  function getColorLevel(level){
  const colors = ['red', 'yellow', 'green']
  switch (level){
    case 'easy':
      return 'green';
    case 'medium':
      return 'orange';
    case 'hard':
      return 'red';
    default:
      return 'blue';
  }
}