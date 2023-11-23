import { Text as RNText, TextProps} from 'react-native';
import { useContext } from 'react';
import { ThemeContext } from '@constants/themes';


export function Text({ children, style, ...props }: TextProps) {
  const theme = useContext(ThemeContext);
  
  const defaultStyle = {
    color: theme.colors.text,
  };

  return (
    <RNText style={[defaultStyle, style]} {...props}>
      {children}
    </RNText>
  );
}
