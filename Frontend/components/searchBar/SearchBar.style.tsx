import { StyleSheet } from 'react-native';
import { DarkTheme, CustomTheme } from '@constants/themes';


export const SearchBarStyles = (theme: CustomTheme) => StyleSheet.create({
  container: {
    width: '100%',
    paddingHorizontal: 10,
    paddingTop: 5,
    paddingBottom: 7.5,
    
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    // backgroundColor: 'green',
  },
  viewContainer: {
    height: '100%',

    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    flexDirection: 'row',

    gap: 10,
    
    // backgroundColor: 'blue',
  },
  pressableContainer: {
    width: '100%',
    height: '100%',
    paddingLeft: 10,

    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-start',
    gap: 5,

    borderRadius: 10,
    overflow: 'hidden',
    backgroundColor: theme.colors.background.brand //'#2A2D43',
  },
  input: {
    flex: 1,
    color: DarkTheme.colors.text,
  },
});